package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.model.UserRole
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun AdminApprovalsScreen(
    approvalsList: List<ApprovalRecordEntity>,
    sellers: List<SellerEntity>,
    documents: List<SellerDocumentEntity>,
    products: List<ProductEntity>,
    payments: List<SellerPaymentEntity>,
    currentUserRole: UserRole,
    onProcessApproval: (entityType: String, entityId: String, entityTitle: String, action: String, prevStatus: String, newStatus: String, comment: String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Pending Approvals Queue, 1: Audit History Log
    var selectedQueueType by remember { mutableStateOf("All") } // All, Sellers, Documents, Products, Payments

    var actionDialogData by remember { mutableStateOf<ApprovalActionData?>(null) }

    val pendingSellers = sellers.filter { it.verificationStatus != "Verified" }
    val pendingDocuments = documents.filter { it.verificationStatus != "VERIFIED" }
    val pendingProducts = products.filter { it.status == "PENDING REVIEW" || it.status == "DRAFT" }
    val pendingPayments = payments.filter { it.paymentStatus != "CONFIRMED" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_approvals_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Operational Approvals Hub",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Multi-tier commercial authorization for merchants, KYC, SKUs & payments",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Tab Row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val totalPending = pendingSellers.size + pendingDocuments.size + pendingProducts.size + pendingPayments.size
            FilterChip(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                label = { Text("Pending Action Queue ($totalPending)", fontSize = 12.sp) }
            )
            FilterChip(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                label = { Text("Decisions & History (${approvalsList.size})", fontSize = 12.sp) }
            )
        }

        if (selectedTab == 0) {
            // Queue Type Selector
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("All", "Sellers (${pendingSellers.size})", "Documents (${pendingDocuments.size})", "Products (${pendingProducts.size})", "Payments (${pendingPayments.size})").forEach { q ->
                    FilterChip(
                        selected = selectedQueueType.startsWith(q.split(" ").first()),
                        onClick = { selectedQueueType = q.split(" ").first() },
                        label = { Text(q, fontSize = 11.sp) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // Sellers Queue
                if (selectedQueueType == "All" || selectedQueueType == "Sellers") {
                    items(pendingSellers, key = { "seller_${it.sellerId}" }) { seller ->
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text("Merchant Verification: ${seller.businessName}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("ID: ${seller.sellerId} • District: ${seller.district} • Rep: ${seller.assignedRepresentative}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    StatusBadge(status = seller.verificationStatus)
                                }
                                Text("Workflow Step: ${seller.workflowStep}", fontSize = 12.sp, color = EmeraldTeal, fontWeight = FontWeight.SemiBold)

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedButton(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("SELLER", seller.sellerId, seller.businessName, "REJECT", seller.verificationStatus, "Rejected")
                                        },
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Reject", color = MaterialTheme.colorScheme.error, fontSize = 11.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("SELLER", seller.sellerId, seller.businessName, "APPROVE", seller.verificationStatus, "Verified")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Verify & Approve", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Documents Queue
                if (selectedQueueType == "All" || selectedQueueType == "Documents") {
                    items(pendingDocuments, key = { "doc_${it.documentId}" }) { doc ->
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text("KYC Document: ${doc.documentType}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("Doc ID: ${doc.documentId} • Seller: ${doc.sellerId} • By: ${doc.uploadedBy}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    StatusBadge(status = doc.verificationStatus)
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    OutlinedButton(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("DOCUMENT", doc.documentId, "${doc.documentType} (${doc.sellerId})", "REJECT", doc.verificationStatus, "REJECTED")
                                        },
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Reject", color = MaterialTheme.colorScheme.error, fontSize = 11.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("DOCUMENT", doc.documentId, "${doc.documentType} (${doc.sellerId})", "APPROVE", doc.verificationStatus, "VERIFIED")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Approve Document", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Products Queue
                if (selectedQueueType == "All" || selectedQueueType == "Products") {
                    items(pendingProducts, key = { "prod_${it.productId}" }) { prod ->
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text("Product SKU: ${prod.productName}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("${prod.productId} • Category: ${prod.category} • Price: ₹${prod.price}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    StatusBadge(status = prod.status)
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    OutlinedButton(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("PRODUCT", prod.productId, prod.productName, "REJECT", prod.status, "SUSPENDED")
                                        },
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Reject SKU", color = MaterialTheme.colorScheme.error, fontSize = 11.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("PRODUCT", prod.productId, prod.productName, "APPROVE", prod.status, "APPROVED")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Approve Catalog SKU", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Payments Queue
                if (selectedQueueType == "All" || selectedQueueType == "Payments") {
                    items(pendingPayments, key = { "pay_${it.paymentId}" }) { pay ->
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text("Payment Verification: ${pay.service}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("${pay.paymentId} • Seller: ${pay.sellerId} • Ref: ${pay.transactionRefNumber}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("₹${pay.amount}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = EmeraldTeal)
                                        StatusBadge(status = pay.paymentStatus)
                                    }
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    OutlinedButton(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("PAYMENT", pay.paymentId, "${pay.service} (₹${pay.amount})", "REJECT", pay.paymentStatus, "FAILED")
                                        },
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Reject", color = MaterialTheme.colorScheme.error, fontSize = 11.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            actionDialogData = ApprovalActionData("PAYMENT", pay.paymentId, "${pay.service} (₹${pay.amount})", "APPROVE", pay.paymentStatus, "CONFIRMED")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Confirm Payment", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Approval Audit History
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(approvalsList, key = { it.approvalId }) { app ->
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${app.action}: ${app.entityTitle}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (app.action == "APPROVE") EmeraldTeal else Color.Red)
                                Text(app.timestamp, fontSize = 11.sp, color = SlateTextMuted)
                            }
                            Text("Authorized by: ${app.userName} (${app.userRole})", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            Text("Transition: ${app.previousStatus} ➔ ${app.newStatus}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (app.comment.isNotBlank()) {
                                Text("Comment: ${app.comment}", fontSize = 11.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                            }
                        }
                    }
                }
            }
        }
    }

    // Action Confirmation Dialog
    actionDialogData?.let { data ->
        var comment by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { actionDialogData = null },
            title = { Text("Confirm ${data.action}: ${data.entityTitle}", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Entity: ${data.entityType} • ID: ${data.entityId}", fontSize = 12.sp)
                    Text("Status will change from '${data.prevStatus}' to '${data.newStatus}'.", fontSize = 12.sp)
                    OutlinedTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        label = { Text("Approval / Decision Notes *") },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onProcessApproval(data.entityType, data.entityId, data.entityTitle, data.action, data.prevStatus, data.newStatus, comment)
                        actionDialogData = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = if (data.action == "APPROVE") EmeraldTeal else MaterialTheme.colorScheme.error)
                ) {
                    Text("Confirm ${data.action}")
                }
            },
            dismissButton = {
                TextButton(onClick = { actionDialogData = null }) { Text("Cancel") }
            }
        )
    }
}

data class ApprovalActionData(
    val entityType: String,
    val entityId: String,
    val entityTitle: String,
    val action: String,
    val prevStatus: String,
    val newStatus: String
)
