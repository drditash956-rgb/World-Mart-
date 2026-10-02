package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.ShopEntity
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminShopScreen(
    sellerList: List<SellerEntity>,
    shopList: List<ShopEntity> = emptyList(),
    onToggleShopStatus: (SellerEntity, String) -> Unit,
    onAddShop: (name: String, owner: String, category: String, address: String, district: String, block: String, city: String, contact: String, rep: String) -> Unit = { _, _, _, _, _, _, _, _, _ -> },
    onTogglePhysicalShopStatus: (ShopEntity, String) -> Unit = { _, _ -> }
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedView by remember { mutableStateOf(0) } // 0: Physical Registered Shops (Shop Database), 1: Live E-Shops
    var showAddShopDialog by remember { mutableStateOf(false) }

    val filteredSellers = sellerList.filter {
        searchQuery.isBlank() ||
                it.businessName.contains(searchQuery, ignoreCase = true) ||
                it.sellerId.contains(searchQuery, ignoreCase = true) ||
                it.district.contains(searchQuery, ignoreCase = true)
    }

    val filteredShops = shopList.filter {
        searchQuery.isBlank() ||
                it.shopName.contains(searchQuery, ignoreCase = true) ||
                it.shopId.contains(searchQuery, ignoreCase = true) ||
                it.owner.contains(searchQuery, ignoreCase = true) ||
                it.district.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_shop_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Shop & E-Store Database",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Manage registered physical retail stores and digital e-shops",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = { showAddShopDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("add_shop_btn")
            ) {
                Icon(Icons.Default.AddBusiness, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Shop", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        // View Selector
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = selectedView == 0,
                onClick = { selectedView = 0 },
                label = { Text("Physical Shops (${shopList.size})", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Store, contentDescription = null, modifier = Modifier.size(14.dp)) }
            )
            FilterChip(
                selected = selectedView == 1,
                onClick = { selectedView = 1 },
                label = { Text("E-Shops (${sellerList.size})", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Storefront, contentDescription = null, modifier = Modifier.size(14.dp)) }
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search shop by name, ID, owner or district...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("shop_search_input"),
            shape = RoundedCornerShape(10.dp)
        )

        if (selectedView == 0) {
            // Physical Shops Database Table / Cards
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(filteredShops, key = { it.shopId }) { shop ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
                                    Text(text = shop.shopName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(
                                        text = "${shop.shopId} • Owner: ${shop.owner}",
                                        fontSize = 11.sp,
                                        color = EmeraldTeal,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    StatusBadge(status = shop.status)
                                    StatusBadge(status = shop.verificationStatus)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Category: ${shop.category}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "E-Shop: ${shop.eshopStatus}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (shop.eshopStatus == "Live") EmeraldTeal else AmberGold
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Text("• Address: ${shop.address}, ${shop.block}, ${shop.city}", fontSize = 11.sp)
                                    Text("• District: ${shop.district} | Contact: ${shop.contact}", fontSize = 11.sp)
                                    Text("• Assigned Representative: ${shop.assignedRepresentative}", fontSize = 11.sp, color = EmeraldTeal)
                                    Text("• Registered Date: ${shop.registrationDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val nextStatus = if (shop.status == "Active") "Inactive" else "Active"
                                OutlinedButton(
                                    onClick = { onTogglePhysicalShopStatus(shop, nextStatus) },
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text(if (shop.status == "Active") "Deactivate" else "Activate", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // E-Shops List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(filteredSellers, key = { it.sellerId }) { seller ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
                                    Text(text = seller.businessName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    val slug = seller.businessName.lowercase().replace(" ", "-").replace("&", "and")
                                    Text(
                                        text = "worldmart.com/shops/$slug",
                                        fontSize = 11.sp,
                                        color = EmeraldTeal,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                StatusBadge(status = seller.eshopStatus)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Catalog: ${seller.productCount} SKUs Listed", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(text = "Rating: ★ ${seller.storeRating}", fontSize = 12.sp, color = AmberGold, fontWeight = FontWeight.Bold)
                            }

                            Text(
                                text = seller.storefrontDescription,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2
                            )

                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "District: ${seller.district}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    if (seller.eshopStatus != "Live") {
                                        Button(
                                            onClick = { onToggleShopStatus(seller, "Live") },
                                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.height(34.dp).testTag("publish_shop_${seller.sellerId}")
                                        ) {
                                            Text("Set Live", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    } else {
                                        OutlinedButton(
                                            onClick = { onToggleShopStatus(seller, "Paused") },
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.height(34.dp).testTag("pause_shop_${seller.sellerId}")
                                        ) {
                                            Text("Pause Storefront", fontSize = 11.sp)
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

    if (showAddShopDialog) {
        AddShopDialog(
            onDismiss = { showAddShopDialog = false },
            onAdd = { n, o, c, a, d, b, ci, cnt, r ->
                onAddShop(n, o, c, a, d, b, ci, cnt, r)
                showAddShopDialog = false
            }
        )
    }
}

@Composable
fun AddShopDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, owner: String, category: String, address: String, district: String, block: String, city: String, contact: String, rep: String) -> Unit
) {
    var shopName by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Groceries & Daily Essentials") }
    var address by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("Central Metro") }
    var block by remember { mutableStateOf("Block-4") }
    var city by remember { mutableStateOf("Metropolis") }
    var contact by remember { mutableStateOf("") }
    var rep by remember { mutableStateOf("Marcus Vance") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Register New Shop Record", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (error != null) {
                    Text(error!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                OutlinedTextField(value = shopName, onValueChange = { shopName = it }, label = { Text("Shop Name *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = owner, onValueChange = { owner = it }, label = { Text("Owner Name *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Physical Address *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = district, onValueChange = { district = it }, label = { Text("District") }, modifier = Modifier.weight(1f), singleLine = true)
                    OutlinedTextField(value = block, onValueChange = { block = it }, label = { Text("Block") }, modifier = Modifier.weight(1f), singleLine = true)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") }, modifier = Modifier.weight(1f), singleLine = true)
                    OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact Phone *") }, modifier = Modifier.weight(1f), singleLine = true)
                }
                OutlinedTextField(value = rep, onValueChange = { rep = it }, label = { Text("Assigned Sales Representative") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (shopName.isBlank() || owner.isBlank() || address.isBlank() || contact.isBlank()) {
                        error = "Please fill in all required fields (*)"
                    } else {
                        onAdd(shopName, owner, category, address, district, block, city, contact, rep)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
            ) {
                Text("Register Shop")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
