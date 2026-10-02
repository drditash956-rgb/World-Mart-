package com.example.ui.admin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import com.example.data.SellerDocumentEntity
import com.example.data.SellerEntity
import com.example.model.UserRole
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDocumentsScreen(
    documents: List<SellerDocumentEntity>,
    sellerList: List<SellerEntity>,
    currentUserRole: UserRole,
    onUploadDocument: (sellerId: String, docType: String, uri: Uri) -> Unit,
    onVerifyDocument: (document: SellerDocumentEntity, status: String, reason: String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("All") }
    var selectedDocForDetail by remember { mutableStateOf<SellerDocumentEntity?>(null) }
    var showUploadDialog by remember { mutableStateOf(false) }

    val filtered = documents.filter { doc ->
        val matchesStatus = selectedStatus == "All" || doc.verificationStatus.equals(selectedStatus, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                doc.sellerId.contains(searchQuery, ignoreCase = true) ||
                doc.documentType.contains(searchQuery, ignoreCase = true) ||
                doc.uploadedBy.contains(searchQuery, ignoreCase = true)
        matchesStatus && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_documents_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Seller KYC Documents",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Encrypted storage & verification for commercial permits, tax IDs & leases",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = { showUploadDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("upload_doc_btn")
            ) {
                Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Upload KYC", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Status Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("All", "PENDING", "UNDER REVIEW", "VERIFIED", "REJECTED").forEach { st ->
                FilterChip(
                    selected = selectedStatus == st,
                    onClick = { selectedStatus = st },
                    label = { Text(st, fontSize = 11.sp) }
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by Seller ID, document type, uploader...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("doc_search_input"),
            shape = RoundedCornerShape(10.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(filtered, key = { it.documentId }) { doc ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { selectedDocForDetail = doc },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = doc.documentType, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(
                                    text = "Doc ID: ${doc.documentId} • Seller: ${doc.sellerId}",
                                    fontSize = 11.sp,
                                    color = EmeraldTeal,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            StatusBadge(status = doc.verificationStatus)
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("• Uploaded by: ${doc.uploadedBy} on ${doc.uploadDate}", fontSize = 11.sp)
                                Text("• Storage File: ${doc.filePath}", fontSize = 10.sp, color = SlateTextMuted, maxLines = 1)
                                if (doc.verifiedBy.isNotBlank()) {
                                    Text("• Verified by: ${doc.verifiedBy} (${doc.verificationDate})", fontSize = 11.sp, color = EmeraldTeal)
                                }
                                if (doc.rejectionReason.isNotBlank()) {
                                    Text("• Rejection Reason: ${doc.rejectionReason}", fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }

                        if (currentUserRole.canVerifyDocuments() && doc.verificationStatus != "VERIFIED") {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                                OutlinedButton(
                                    onClick = { selectedDocForDetail = doc },
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Review & Verify", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Detail & Verification Dialog
    selectedDocForDetail?.let { doc ->
        var rejectionReason by remember { mutableStateOf("") }
        var showRejectBox by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { selectedDocForDetail = null },
            title = { Text("KYC Document Verification", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Document: ${doc.documentType}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Document ID: ${doc.documentId}", fontSize = 12.sp)
                    Text("Seller: ${doc.sellerId}", fontSize = 12.sp)
                    Text("Uploaded by: ${doc.uploadedBy} (${doc.uploadDate})", fontSize = 12.sp)
                    Text("Secure Path: ${doc.filePath}", fontSize = 11.sp, color = SlateTextMuted)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Current Status:", fontSize = 12.sp)
                        StatusBadge(status = doc.verificationStatus)
                    }

                    if (showRejectBox) {
                        OutlinedTextField(
                            value = rejectionReason,
                            onValueChange = { rejectionReason = it },
                            label = { Text("Rejection Reason *") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                if (currentUserRole.canVerifyDocuments()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (!showRejectBox) {
                            Button(
                                onClick = {
                                    onVerifyDocument(doc, "VERIFIED", "")
                                    selectedDocForDetail = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
                            ) {
                                Text("Approve / Verify")
                            }
                            OutlinedButton(
                                onClick = { showRejectBox = true }
                            ) {
                                Text("Reject...", color = MaterialTheme.colorScheme.error)
                            }
                        } else {
                            Button(
                                onClick = {
                                    if (rejectionReason.isNotBlank()) {
                                        onVerifyDocument(doc, "REJECTED", rejectionReason)
                                        selectedDocForDetail = null
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Confirm Rejection")
                            }
                        }
                    }
                } else {
                    TextButton(onClick = { selectedDocForDetail = null }) { Text("Close") }
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedDocForDetail = null }) { Text("Cancel") }
            }
        )
    }

    // Upload Document Dialog
    if (showUploadDialog) {
        UploadDocumentDialog(
            sellerList = sellerList,
            onDismiss = { showUploadDialog = false },
            onUpload = { sId, dType, uri ->
                onUploadDocument(sId, dType, uri)
                showUploadDialog = false
            }
        )
    }
}

@Composable
fun UploadDocumentDialog(
    sellerList: List<SellerEntity>,
    onDismiss: () -> Unit,
    onUpload: (sellerId: String, docType: String, uri: Uri) -> Unit
) {
    var selectedSellerId by remember { mutableStateOf(sellerList.firstOrNull()?.sellerId ?: "WM-SLR-1001") }
    val docTypes = listOf(
        "Commercial Trade License",
        "GST / Business Tax ID",
        "Food Safety & Quality Certificate",
        "Commercial Lease Agreement",
        "Proprietor Government National ID",
        "Bank Cheque / Statement",
        "Premises NOC"
    )
    var selectedDocType by remember { mutableStateOf(docTypes.first()) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val filePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Upload Seller KYC Document", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                Text("Target Seller:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    sellerList.forEach { s ->
                        FilterChip(
                            selected = selectedSellerId == s.sellerId,
                            onClick = { selectedSellerId = s.sellerId },
                            label = { Text("${s.sellerId} (${s.businessName.take(12)})", fontSize = 11.sp) }
                        )
                    }
                }

                Text("Document Type:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    docTypes.take(4).forEach { t ->
                        FilterChip(
                            selected = selectedDocType == t,
                            onClick = { selectedDocType = t },
                            label = { Text(t, fontSize = 11.sp) }
                        )
                    }
                }

                OutlinedButton(
                    onClick = { filePicker.launch("*/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AttachFile, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (selectedUri != null) "File Attached ✓" else "Select Document File...")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val uri = selectedUri ?: Uri.parse("content://worldmart/docs/sample_kyc.pdf")
                    onUpload(selectedSellerId, selectedDocType, uri)
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
            ) {
                Text("Save Document")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
