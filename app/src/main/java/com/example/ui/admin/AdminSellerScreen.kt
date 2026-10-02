package com.example.ui.admin

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SellerEntity
import com.example.model.UserRole
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSellerScreen(
    sellerList: List<SellerEntity>,
    currentUserRole: UserRole,
    onUpdateStatus: (seller: SellerEntity, newSellerStatus: String?, newVerificationStatus: String?, newEshopStatus: String?, newRep: String?) -> Unit,
    onAddSeller: (businessName: String, category: String, ownerName: String, location: String, district: String, phone: String, email: String, description: String, rep: String) -> Unit,
    onDeleteSeller: (SellerEntity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("All") }
    var selectedStatus by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingSeller by remember { mutableStateOf<SellerEntity?>(null) }

    val districts = listOf("All", "Central Metro", "West Harbor", "East Valley", "North Park", "South Tech Hub")
    val statuses = listOf("All", "Active", "Pending Review", "Suspended")

    // Role-based filtering: Sales Rep only sees their assigned sellers!
    val roleFilteredList = if (currentUserRole == UserRole.SALES_REP) {
        sellerList.filter { it.assignedRepresentative.contains("Marcus Vance", ignoreCase = true) }
    } else {
        sellerList
    }

    val filtered = roleFilteredList.filter { s ->
        val matchesDistrict = selectedDistrict == "All" || s.district.equals(selectedDistrict, ignoreCase = true)
        val matchesStatus = selectedStatus == "All" || s.sellerStatus.equals(selectedStatus, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                s.businessName.contains(searchQuery, ignoreCase = true) ||
                s.sellerId.contains(searchQuery, ignoreCase = true) ||
                s.ownerName.contains(searchQuery, ignoreCase = true) ||
                s.businessCategory.contains(searchQuery, ignoreCase = true)
        matchesDistrict && matchesStatus && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_seller_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "WORLD_MART_SELLERS Collection",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Active Merchant Database (${roleFilteredList.size} sellers)",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (currentUserRole != UserRole.SALES_REP) {
                Button(
                    onClick = { showAddDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("admin_add_seller_button")
                ) {
                    Icon(Icons.Default.AddBusiness, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Seller", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Sales Rep Restricted View Notice
        if (currentUserRole == UserRole.SALES_REP) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF3C7),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = AmberGold)
                    Text(
                        text = "Sales Rep View: Showing only your assigned merchants (Marcus Vance - Central & West Districts).",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF78350F)
                    )
                }
            }
        }

        // Search & Filters
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by business, owner, ID, category...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("admin_seller_search"),
            shape = RoundedCornerShape(10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            districts.forEach { dist ->
                FilterChip(
                    selected = selectedDistrict == dist,
                    onClick = { selectedDistrict = dist },
                    label = { Text(dist, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldTeal,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Sellers List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(filtered, key = { it.sellerId }) { seller ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("seller_card_${seller.sellerId}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = seller.businessName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Category: ${seller.businessCategory} • Ref: ${seller.sellerId}",
                                    fontSize = 11.sp,
                                    color = EmeraldTeal,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            StatusBadge(status = seller.sellerStatus)
                        }

                        // Detailed Mandated Fields Grid
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(text = "• Owner/Business Name: ${seller.ownerName}", fontSize = 11.sp)
                            Text(text = "• Location & District: ${seller.location} (${seller.district})", fontSize = 11.sp)
                            Text(text = "• Contact: ${seller.contactPhone} | ${seller.contactEmail}", fontSize = 11.sp)
                            Text(text = "• Registration Date: ${seller.registrationDate}", fontSize = 11.sp)
                            Text(text = "• Assigned Representative: ${seller.assignedRepresentative}", fontSize = 11.sp, color = AmberGold, fontWeight = FontWeight.SemiBold)
                            Text(text = "• Product Count: ${seller.productCount} SKUs", fontSize = 11.sp)
                        }

                        // Status Badges & Quick Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                StatusBadge(status = seller.verificationStatus)
                                StatusBadge(status = "Shop: ${seller.eshopStatus}")
                            }

                            // Quick Action Buttons
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { editingSeller = seller },
                                    modifier = Modifier.testTag("edit_seller_${seller.sellerId}")
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit Status", tint = EmeraldTeal)
                                }
                                if (currentUserRole in listOf(UserRole.FOUNDER_CEO, UserRole.OPERATIONS)) {
                                    IconButton(
                                        onClick = { onDeleteSeller(seller) },
                                        modifier = Modifier.testTag("delete_seller_${seller.sellerId}")
                                    ) {
                                        Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Edit Status Dialog
        editingSeller?.let { seller ->
            EditSellerStatusDialog(
                seller = seller,
                onDismiss = { editingSeller = null },
                onSave = { sStatus, vStatus, eStatus, rep ->
                    onUpdateStatus(seller, sStatus, vStatus, eStatus, rep)
                    editingSeller = null
                }
            )
        }

        // Add Seller Dialog
        if (showAddDialog) {
            AddSellerAdminDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { bn, cat, on, loc, dist, ph, em, desc, rep ->
                    onAddSeller(bn, cat, on, loc, dist, ph, em, desc, rep)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun EditSellerStatusDialog(
    seller: SellerEntity,
    onDismiss: () -> Unit,
    onSave: (sStatus: String, vStatus: String, eStatus: String, rep: String) -> Unit
) {
    var sellerStatus by remember { mutableStateOf(seller.sellerStatus) }
    var verificationStatus by remember { mutableStateOf(seller.verificationStatus) }
    var eshopStatus by remember { mutableStateOf(seller.eshopStatus) }
    var rep by remember { mutableStateOf(seller.assignedRepresentative) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update: ${seller.businessName}", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "Seller Status:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Active", "Pending Review", "Suspended").forEach { st ->
                        FilterChip(
                            selected = sellerStatus == st,
                            onClick = { sellerStatus = st },
                            label = { Text(st, fontSize = 11.sp) }
                        )
                    }
                }

                Text(text = "Verification Status:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Verified", "Pending Documents", "Unverified").forEach { st ->
                        FilterChip(
                            selected = verificationStatus == st,
                            onClick = { verificationStatus = st },
                            label = { Text(st, fontSize = 11.sp) }
                        )
                    }
                }

                Text(text = "E-Shop Storefront Status:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Live", "In Setup", "Paused").forEach { st ->
                        FilterChip(
                            selected = eshopStatus == st,
                            onClick = { eshopStatus = st },
                            label = { Text(st, fontSize = 11.sp) }
                        )
                    }
                }

                OutlinedTextField(
                    value = rep,
                    onValueChange = { rep = it },
                    label = { Text("Assigned Representative") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(sellerStatus, verificationStatus, eshopStatus, rep) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
            ) {
                Text("Save Changes", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSellerAdminDialog(
    onDismiss: () -> Unit,
    onAdd: (
        businessName: String, category: String, ownerName: String, location: String,
        district: String, phone: String, email: String, description: String, rep: String
    ) -> Unit
) {
    var businessName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rep by remember { mutableStateOf("Marcus Vance") }

    val categories = listOf("Electronics", "Fresh Groceries", "Fashion", "Home & Living", "Health & Wellness", "Automotive")
    var selectedCat by remember { mutableStateOf(categories.first()) }

    val districts = listOf("Central Metro", "West Harbor", "East Valley", "North Park", "South Tech Hub")
    var selectedDist by remember { mutableStateOf(districts.first()) }

    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Seller to WORLD_MART_SELLERS", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = businessName, onValueChange = { businessName = it }, label = { Text("Business Name *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = ownerName, onValueChange = { ownerName = it }, label = { Text("Owner Name *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Street Address *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = rep, onValueChange = { rep = it }, label = { Text("Assigned Rep") }, modifier = Modifier.fillMaxWidth())
                if (error != null) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (businessName.isBlank() || ownerName.isBlank() || location.isBlank() || phone.isBlank()) {
                        error = "Please fill in all mandatory fields"
                        return@Button
                    }
                    onAdd(businessName, selectedCat, ownerName, location, selectedDist, phone, email, description, rep)
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
            ) {
                Text("Add Seller", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
