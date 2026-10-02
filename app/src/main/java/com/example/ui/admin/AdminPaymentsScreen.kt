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
import androidx.compose.foundation.verticalScroll
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
import com.example.data.SellerEntity
import com.example.data.SellerPaymentEntity
import com.example.data.ServicePricingEntity
import com.example.data.ShopEntity
import com.example.model.UserRole
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPaymentsScreen(
    payments: List<SellerPaymentEntity>,
    pricings: List<ServicePricingEntity>,
    sellerList: List<SellerEntity>,
    shopList: List<ShopEntity>,
    currentUserRole: UserRole,
    onRecordPayment: (sellerId: String, shopId: String, service: String, amount: Double, method: String, refNumber: String, receiptUri: Uri?, notes: String) -> Unit,
    onVerifyPayment: (payment: SellerPaymentEntity, status: String, notes: String) -> Unit,
    onUpdatePricing: (ServicePricingEntity) -> Unit,
    onAddPricing: (name: String, category: String, price: Double, district: String, desc: String) -> Unit
) {
    var selectedView by remember { mutableStateOf(0) } // 0: Payment Ledger, 1: Service Pricing Configuration
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("All") }
    var showRecordPaymentDialog by remember { mutableStateOf(false) }
    var showAddPricingDialog by remember { mutableStateOf(false) }
    var verifyingPayment by remember { mutableStateOf<SellerPaymentEntity?>(null) }

    val filteredPayments = payments.filter { p ->
        val matchesStatus = selectedStatus == "All" || p.paymentStatus.equals(selectedStatus, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                p.paymentId.contains(searchQuery, ignoreCase = true) ||
                p.sellerId.contains(searchQuery, ignoreCase = true) ||
                p.service.contains(searchQuery, ignoreCase = true) ||
                p.transactionRefNumber.contains(searchQuery, ignoreCase = true)
        matchesStatus && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_payments_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Seller Payments & Fee Ledger",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Commercial onboarding, cataloging fees, receipts & price configurator",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (selectedView == 0) {
                Button(
                    onClick = { showRecordPaymentDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("record_payment_btn")
                ) {
                    Icon(Icons.Default.AddCard, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Collect Fee", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            } else if (currentUserRole.canConfigurePricing()) {
                Button(
                    onClick = { showAddPricingDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New Pricing", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CorporateNavyDark)
                }
            }
        }

        // View Tabs
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = selectedView == 0,
                onClick = { selectedView = 0 },
                label = { Text("Payments Ledger (${payments.size})", fontSize = 12.sp) }
            )
            FilterChip(
                selected = selectedView == 1,
                onClick = { selectedView = 1 },
                label = { Text("Configurable Pricing Schedule (${pricings.size})", fontSize = 12.sp) }
            )
        }

        if (selectedView == 0) {
            // Filter by status
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("All", "PENDING", "SUBMITTED", "UNDER REVIEW", "CONFIRMED", "FAILED", "REFUNDED").forEach { st ->
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
                placeholder = { Text("Search by Payment ID, Seller ID, Ref, service...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("payment_search_input"),
                shape = RoundedCornerShape(10.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(filteredPayments, key = { it.paymentId }) { pay ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(pay.service, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(
                                        text = "${pay.paymentId} • Seller: ${pay.sellerId} • Shop: ${pay.shopId}",
                                        fontSize = 11.sp,
                                        color = EmeraldTeal,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("₹${pay.amount}", fontWeight = FontWeight.Black, fontSize = 16.sp, color = EmeraldTeal)
                                    StatusBadge(status = pay.paymentStatus)
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text("• Method: ${pay.paymentMethod} | Ref: ${pay.transactionRefNumber}", fontSize = 11.sp)
                                    Text("• Date: ${pay.date} | Collected by: ${pay.collectedBy}", fontSize = 11.sp)
                                    if (pay.verifiedBy.isNotBlank()) {
                                        Text("• Verified by: ${pay.verifiedBy}", fontSize = 11.sp, color = EmeraldTeal)
                                    }
                                    if (pay.receiptPath.isNotBlank()) {
                                        Text("• Receipt: Attached (${pay.receiptPath.substringAfterLast("/")})", fontSize = 10.sp, color = SlateTextMuted)
                                    }
                                    if (pay.notes.isNotBlank()) {
                                        Text("• Notes: ${pay.notes}", fontSize = 11.sp)
                                    }
                                }
                            }

                            if (currentUserRole.canVerifyPayments() && pay.paymentStatus != "CONFIRMED") {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    Button(
                                        onClick = { verifyingPayment = pay },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Finance Verification", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Pricing Configuration View
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(pricings, key = { it.serviceId }) { pricing ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(pricing.serviceName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Category: ${pricing.category} • Applicable: ${pricing.district}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text("₹${pricing.basePrice}", fontWeight = FontWeight.Black, fontSize = 17.sp, color = AmberGold)
                            }
                            Text(pricing.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            if (currentUserRole.canConfigurePricing()) {
                                var editMode by remember { mutableStateOf(false) }
                                var newPriceText by remember { mutableStateOf(pricing.basePrice.toString()) }

                                if (!editMode) {
                                    OutlinedButton(
                                        onClick = { editMode = true },
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text("Adjust Price", fontSize = 11.sp)
                                    }
                                } else {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        OutlinedTextField(
                                            value = newPriceText,
                                            onValueChange = { newPriceText = it },
                                            label = { Text("Base Fee (₹)") },
                                            singleLine = true,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Button(
                                            onClick = {
                                                val p = newPriceText.toDoubleOrNull() ?: pricing.basePrice
                                                onUpdatePricing(pricing.copy(basePrice = p))
                                                editMode = false
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
                                        ) {
                                            Text("Save")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Payment Verification Dialog
    verifyingPayment?.let { pay ->
        var notes by remember { mutableStateOf("") }
        var status by remember { mutableStateOf("CONFIRMED") }

        AlertDialog(
            onDismissRequest = { verifyingPayment = null },
            title = { Text("Verify Payment ${pay.paymentId}", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Service: ${pay.service}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Amount: ₹${pay.amount}", fontSize = 14.sp, fontWeight = FontWeight.Black, color = EmeraldTeal)
                    Text("Transaction Reference: ${pay.transactionRefNumber}", fontSize = 12.sp)
                    Text("Collected by: ${pay.collectedBy}", fontSize = 12.sp)

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("CONFIRMED", "UNDER REVIEW", "FAILED", "REFUNDED").forEach { s ->
                            FilterChip(
                                selected = status == s,
                                onClick = { status = s },
                                label = { Text(s, fontSize = 11.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Finance Verification Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onVerifyPayment(pay, status, notes)
                        verifyingPayment = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
                ) {
                    Text("Confirm Status")
                }
            },
            dismissButton = {
                TextButton(onClick = { verifyingPayment = null }) { Text("Cancel") }
            }
        )
    }

    // Record Payment Dialog
    if (showRecordPaymentDialog) {
        RecordPaymentDialog(
            pricings = pricings,
            sellerList = sellerList,
            shopList = shopList,
            onDismiss = { showRecordPaymentDialog = false },
            onRecord = { sId, shId, svc, amt, m, ref, uri, nts ->
                onRecordPayment(sId, shId, svc, amt, m, ref, uri, nts)
                showRecordPaymentDialog = false
            }
        )
    }

    // Add Pricing Dialog
    if (showAddPricingDialog) {
        AddPricingDialog(
            onDismiss = { showAddPricingDialog = false },
            onAdd = { n, c, p, d, desc ->
                onAddPricing(n, c, p, d, desc)
                showAddPricingDialog = false
            }
        )
    }
}

@Composable
fun RecordPaymentDialog(
    pricings: List<ServicePricingEntity>,
    sellerList: List<SellerEntity>,
    shopList: List<ShopEntity>,
    onDismiss: () -> Unit,
    onRecord: (sellerId: String, shopId: String, service: String, amount: Double, method: String, refNumber: String, uri: Uri?, notes: String) -> Unit
) {
    var selectedSellerId by remember { mutableStateOf(sellerList.firstOrNull()?.sellerId ?: "WM-SLR-1001") }
    var selectedShopId by remember { mutableStateOf(shopList.firstOrNull()?.shopId ?: "WM-SHP-201") }
    var selectedService by remember { mutableStateOf(pricings.firstOrNull()?.serviceName ?: "Merchant Onboarding Fee") }
    var amountText by remember { mutableStateOf(pricings.firstOrNull()?.basePrice?.toString() ?: "1500.0") }
    var method by remember { mutableStateOf("UPI") }
    var refNumber by remember { mutableStateOf("UPI-REF-${(100000..999999).random()}") }
    var notes by remember { mutableStateOf("") }
    var receiptUri by remember { mutableStateOf<Uri?>(null) }

    val filePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        receiptUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Record Seller Fee Payment", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Select Seller:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    sellerList.forEach { s ->
                        FilterChip(
                            selected = selectedSellerId == s.sellerId,
                            onClick = {
                                selectedSellerId = s.sellerId
                                selectedShopId = s.shopId.ifBlank { "WM-SHP-201" }
                            },
                            label = { Text("${s.sellerId} (${s.businessName.take(10)})", fontSize = 11.sp) }
                        )
                    }
                }

                Text("Select Service / Fee Type:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    pricings.forEach { p ->
                        FilterChip(
                            selected = selectedService == p.serviceName,
                            onClick = {
                                selectedService = p.serviceName
                                amountText = p.basePrice.toString()
                            },
                            label = { Text("${p.serviceName.take(18)} (₹${p.basePrice.toInt()})", fontSize = 11.sp) }
                        )
                    }
                }

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount (₹) *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("UPI", "Cash", "Bank Transfer", "Card", "Escrow").forEach { m ->
                        FilterChip(selected = method == m, onClick = { method = m }, label = { Text(m, fontSize = 11.sp) })
                    }
                }

                OutlinedTextField(
                    value = refNumber,
                    onValueChange = { refNumber = it },
                    label = { Text("Transaction Reference Number *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = { filePicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (receiptUri != null) "Receipt Attached ✓" else "Attach Receipt Photo...")
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes / Collection Details") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amt = amountText.toDoubleOrNull() ?: 1500.0
                    onRecord(selectedSellerId, selectedShopId, selectedService, amt, method, refNumber, receiptUri, notes)
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
            ) {
                Text("Record Payment")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddPricingDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, category: String, price: Double, district: String, desc: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Onboarding") }
    var priceText by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("All Districts") }
    var desc by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Configurable Service Price", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Service Name *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = priceText, onValueChange = { priceText = it }, label = { Text("Base Price (₹) *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = district, onValueChange = { district = it }, label = { Text("District Application") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val p = priceText.toDoubleOrNull() ?: 1000.0
                    if (name.isNotBlank()) onAdd(name, category, p, district, desc)
                },
                colors = ButtonDefaults.buttonColors(containerColor = AmberGold)
            ) {
                Text("Configure Price", color = CorporateNavyDark)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
