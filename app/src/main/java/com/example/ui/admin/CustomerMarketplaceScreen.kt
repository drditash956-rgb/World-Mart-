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
import com.example.data.*
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun CustomerMarketplaceScreen(
    currentUser: UserAccountEntity?,
    products: List<ProductEntity>,
    sellers: List<SellerEntity>,
    shops: List<ShopEntity>,
    cartItems: List<CartItemEntity>,
    orders: List<OrderEntity>,
    onAddToCart: (ProductEntity) -> Unit,
    onUpdateCartQty: (CartItemEntity, Int) -> Unit,
    onRemoveCartItem: (CartItemEntity) -> Unit,
    onPlaceOrder: (address: String, district: String, method: String) -> Unit,
    onOpenAuthDialog: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Browse Products, 1: Local Shops, 2: Cart, 3: Orders, 4: Profile
    val tabs = listOf("Browse", "Local Shops", "Cart (${cartItems.sumOf { it.quantity }})", "Orders (${orders.size})", "Profile")

    var searchQuery by remember { mutableStateOf("") }
    val categories = listOf("All", "Electronics", "Fresh Groceries", "Fashion", "Home & Living", "Health & Wellness", "Automotive")
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredProducts = products.filter { p ->
        val matchesCat = selectedCategory == "All" || p.category.equals(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                p.productName.contains(searchQuery, ignoreCase = true) ||
                p.brand.contains(searchQuery, ignoreCase = true) ||
                p.category.contains(searchQuery, ignoreCase = true)
        matchesCat && matchesSearch && p.status == "Active"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("customer_marketplace_screen")
    ) {
        // Customer Header
        Surface(color = CorporateNavyDark) {
            Column(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("WORLD MART MARKETPLACE", color = EmeraldTealLight, fontWeight = FontWeight.Bold, fontSize = 11.sp, letterSpacing = 1.sp)
                        Text("Shop from Local Neighborhood Stores", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    if (currentUser == null) {
                        Button(
                            onClick = onOpenAuthDialog,
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("Sign In", fontSize = 11.sp)
                        }
                    } else {
                        Text("Hi, ${currentUser.fullName.split(" ").first()}", color = AmberGoldLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Sub Tabs
        TabRow(selectedTabIndex = selectedTab, containerColor = MaterialTheme.colorScheme.surface, contentColor = EmeraldTeal) {
            tabs.forEachIndexed { idx, title ->
                Tab(
                    selected = selectedTab == idx,
                    onClick = { selectedTab = idx },
                    text = { Text(title, fontSize = 11.sp, fontWeight = if (selectedTab == idx) FontWeight.Bold else FontWeight.Normal) }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize().padding(14.dp)) {
            when (selectedTab) {
                0 -> MarketplaceBrowseTab(
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it },
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategoryChange = { selectedCategory = it },
                    products = filteredProducts,
                    onAddToCart = onAddToCart
                )
                1 -> MarketplaceShopsTab(shops = shops, sellers = sellers)
                2 -> MarketplaceCartTab(
                    cartItems = cartItems,
                    currentUser = currentUser,
                    onUpdateQty = onUpdateCartQty,
                    onRemove = onRemoveCartItem,
                    onPlaceOrder = onPlaceOrder,
                    onOpenAuth = onOpenAuthDialog
                )
                3 -> MarketplaceOrdersTab(orders = orders)
                4 -> MarketplaceProfileTab(currentUser = currentUser, onOpenAuth = onOpenAuthDialog)
            }
        }
    }
}

@Composable
fun MarketplaceBrowseTab(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    categories: List<String>,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    products: List<ProductEntity>,
    onAddToCart: (ProductEntity) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Search products, brands, groceries...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )

        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            categories.forEach { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { onCategoryChange(cat) },
                    label = { Text(cat, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldTeal,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(products, key = { it.productId }) { prod ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFCCFBF1)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(24.dp))
                        }

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(prod.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Brand: ${prod.brand} • SKU: ${prod.sku}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("$${String.format("%.2f", prod.price)}", fontWeight = FontWeight.Black, fontSize = 14.sp, color = EmeraldTeal)
                        }

                        Button(
                            onClick = { onAddToCart(prod) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(34.dp).testTag("add_to_cart_${prod.productId}")
                        ) {
                            Text("Add to Cart", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MarketplaceShopsTab(shops: List<ShopEntity>, sellers: List<SellerEntity>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Neighborhood Verified Storefronts (${shops.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        items(shops, key = { it.shopId }) { shop ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(shop.shopName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        StatusBadge(status = shop.eshopStatus)
                    }
                    Text("Category: ${shop.category} • District: ${shop.district}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Location: ${shop.address}, ${shop.city}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun MarketplaceCartTab(
    cartItems: List<CartItemEntity>,
    currentUser: UserAccountEntity?,
    onUpdateQty: (CartItemEntity, Int) -> Unit,
    onRemove: (CartItemEntity) -> Unit,
    onPlaceOrder: (address: String, district: String, method: String) -> Unit,
    onOpenAuth: () -> Unit
) {
    if (cartItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.RemoveShoppingCart, contentDescription = null, tint = SlateTextMuted, modifier = Modifier.size(48.dp))
                Text("Your shopping cart is currently empty.")
                Text("Browse products and add items to your multi-store cart.", fontSize = 12.sp, color = SlateTextMuted)
            }
        }
        return
    }

    var shippingAddress by remember { mutableStateOf("45 Willow Street, Apt 4B") }
    var district by remember { mutableStateOf("Central Metro") }
    val totalAmount = cartItems.sumOf { it.price * it.quantity }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Your Unified Cart (${cartItems.size} items)", fontWeight = FontWeight.Bold, fontSize = 15.sp)

        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(cartItems, key = { it.cartItemId }) { item ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("$${String.format("%.2f", item.price)} each", fontSize = 11.sp, color = EmeraldTeal)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(onClick = { onUpdateQty(item, item.quantity - 1) }, modifier = Modifier.size(28.dp)) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                            }
                            Text("${item.quantity}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            IconButton(onClick = { onUpdateQty(item, item.quantity + 1) }, modifier = Modifier.size(28.dp)) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
                            }
                        }

                        IconButton(onClick = { onRemove(item) }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.DeleteOutline, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }

        // Checkout Box
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                OutlinedTextField(value = shippingAddress, onValueChange = { shippingAddress = it }, label = { Text("Delivery Address") }, modifier = Modifier.fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Checkout Amount:", fontWeight = FontWeight.Bold)
                    Text("$${String.format("%.2f", totalAmount)}", fontWeight = FontWeight.Black, fontSize = 16.sp, color = EmeraldTeal)
                }

                Button(
                    onClick = { onPlaceOrder(shippingAddress, district, "WorldMart Pay Escrow") },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                    modifier = Modifier.fillMaxWidth().height(46.dp).testTag("customer_place_order_button")
                ) {
                    Text("Confirm 2-Hour Order (Escrow Checkout)", fontWeight = FontWeight.Bold, color = CorporateNavyDark)
                }
            }
        }
    }
}

@Composable
fun MarketplaceOrdersTab(orders: List<OrderEntity>) {
    if (orders.isEmpty()) {
        Text("No orders placed yet.")
        return
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Order History & Delivery Tracking (${orders.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        items(orders, key = { it.orderId }) { order ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(order.orderId, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        StatusBadge(status = order.orderStatus)
                    }
                    Text("Items: ${order.itemsSummary}", fontSize = 11.sp)
                    Text("Estimated Delivery: ${order.estimatedDelivery}", fontSize = 11.sp, color = EmeraldTeal, fontWeight = FontWeight.SemiBold)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Date: ${order.orderDate}", fontSize = 10.sp, color = SlateTextMuted)
                        Text("$${String.format("%.2f", order.totalAmount)}", fontWeight = FontWeight.Black, color = EmeraldTeal)
                    }
                }
            }
        }
    }
}

@Composable
fun MarketplaceProfileTab(currentUser: UserAccountEntity?, onOpenAuth: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Customer Profile & Account", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        if (currentUser != null) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(currentUser.fullName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Email: ${currentUser.email}", fontSize = 12.sp)
                    Text("Username: ${currentUser.username} • Role: ${currentUser.roleName}", fontSize = 11.sp, color = EmeraldTeal)
                    Text("Account Status: ${currentUser.status}", fontSize = 11.sp, color = StatusSuccess)
                }
            }
        } else {
            Button(onClick = onOpenAuth, colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)) {
                Text("Sign In / Register Customer Account")
            }
        }
    }
}
