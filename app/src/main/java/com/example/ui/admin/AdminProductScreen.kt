package com.example.ui.admin

import androidx.compose.foundation.background
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
import com.example.data.ProductEntity
import com.example.data.SellerEntity
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProductScreen(
    productList: List<ProductEntity>,
    sellerList: List<SellerEntity>,
    onAddProduct: (sellerId: String, name: String, category: String, subcategory: String, desc: String, price: Double, stock: Int, photo: String) -> Unit,
    onUpdateStatus: (ProductEntity, String, String) -> Unit,
    onDeleteProduct: (ProductEntity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }

    val categories = listOf("All", "Electronics", "Fresh Groceries", "Fashion", "Home & Living", "Health & Wellness", "Automotive")

    val filtered = productList.filter { p ->
        val matchesCat = selectedCategory == "All" || p.category.equals(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                p.productName.contains(searchQuery, ignoreCase = true) ||
                p.productId.contains(searchQuery, ignoreCase = true) ||
                p.subcategory.contains(searchQuery, ignoreCase = true)
        matchesCat && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_product_screen"),
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
                    text = "WORLD_MART_PRODUCTS Collection",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "${productList.size} Catalog SKUs indexed across merchants",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("admin_add_product_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Product", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search product name, ID, or subcategory...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("product_search_input"),
            shape = RoundedCornerShape(10.dp)
        )

        // Products List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(filtered, key = { it.productId }) { prod ->
                val seller = sellerList.find { it.sellerId == prod.sellerId }

                Card(
                    modifier = Modifier.fillMaxWidth().testTag("product_card_${prod.productId}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = prod.productName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(
                                    text = "Ref: ${prod.productId} • Seller: ${seller?.businessName ?: prod.sellerId}",
                                    fontSize = 11.sp,
                                    color = EmeraldTeal,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = "$${String.format("%.2f", prod.price)}",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Product Details
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(text = "• Category: ${prod.category} (${prod.subcategory})", fontSize = 11.sp)
                            Text(text = "• Stock: ${prod.stockQuantity} units available", fontSize = 11.sp)
                            Text(text = "• Description: ${prod.description}", fontSize = 11.sp, maxLines = 2)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                StatusBadge(status = prod.availability)
                                StatusBadge(status = prod.status)
                            }

                            Row {
                                IconButton(
                                    onClick = {
                                        val nextStatus = if (prod.status == "Active") "Archived" else "Active"
                                        onUpdateStatus(prod, nextStatus, prod.availability)
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (prod.status == "Active") Icons.Default.Archive else Icons.Default.Unarchive,
                                        contentDescription = "Toggle Archive",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                IconButton(onClick = { onDeleteProduct(prod) }) {
                                    Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add Product Dialog
        if (showAddDialog) {
            AddProductDialog(
                sellerList = sellerList,
                onDismiss = { showAddDialog = false },
                onAdd = { sId, n, cat, scat, desc, pr, stk, photo ->
                    onAddProduct(sId, n, cat, scat, desc, pr, stk, photo)
                    showAddDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    sellerList: List<SellerEntity>,
    onDismiss: () -> Unit,
    onAdd: (sellerId: String, name: String, category: String, subcategory: String, desc: String, price: Double, stock: Int, photo: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var subcategory by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("25") }

    val sellers = sellerList.ifEmpty { com.example.data.InitialData.sellerList }
    var selectedSeller by remember { mutableStateOf(sellers.first()) }
    var sellerDropdownExpanded by remember { mutableStateOf(false) }

    val categories = listOf("Electronics", "Fresh Groceries", "Fashion", "Home & Living", "Health & Wellness", "Automotive")
    var selectedCat by remember { mutableStateOf(categories.first()) }
    var catDropdownExpanded by remember { mutableStateOf(false) }

    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Product to WORLD_MART_PRODUCTS", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Link Seller
                ExposedDropdownMenuBox(
                    expanded = sellerDropdownExpanded,
                    onExpandedChange = { sellerDropdownExpanded = !sellerDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = "${selectedSeller.businessName} (${selectedSeller.sellerId})",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Linked Seller *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sellerDropdownExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = sellerDropdownExpanded,
                        onDismissRequest = { sellerDropdownExpanded = false }
                    ) {
                        sellers.forEach { s ->
                            DropdownMenuItem(text = { Text("${s.businessName} (${s.sellerId})") }, onClick = { selectedSeller = s; sellerDropdownExpanded = false })
                        }
                    }
                }

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Product Name *") }, modifier = Modifier.fillMaxWidth().testTag("add_product_name"))

                // Category
                ExposedDropdownMenuBox(
                    expanded = catDropdownExpanded,
                    onExpandedChange = { catDropdownExpanded = !catDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCat,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = catDropdownExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = catDropdownExpanded,
                        onDismissRequest = { catDropdownExpanded = false }
                    ) {
                        categories.forEach { c ->
                            DropdownMenuItem(text = { Text(c) }, onClick = { selectedCat = c; catDropdownExpanded = false })
                        }
                    }
                }

                OutlinedTextField(value = subcategory, onValueChange = { subcategory = it }, label = { Text("Subcategory (e.g. Audio, Menswear)") }, modifier = Modifier.fillMaxWidth())

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = priceText, onValueChange = { priceText = it }, label = { Text("Price ($) *") }, modifier = Modifier.weight(1f).testTag("add_product_price"))
                    OutlinedTextField(value = stockText, onValueChange = { stockText = it }, label = { Text("Initial Stock") }, modifier = Modifier.weight(1f))
                }

                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description *") }, minLines = 2, modifier = Modifier.fillMaxWidth())

                if (error != null) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull()
                    val stock = stockText.toIntOrNull() ?: 10
                    if (name.isBlank() || price == null || price <= 0 || desc.isBlank()) {
                        error = "Please provide valid product name, price, and description"
                        return@Button
                    }
                    onAdd(selectedSeller.sellerId, name, selectedCat, subcategory.ifBlank { "General" }, desc, price, stock, "box")
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                modifier = Modifier.testTag("confirm_add_product_button")
            ) {
                Text("Publish to Catalog", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
