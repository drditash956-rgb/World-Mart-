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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.model.SellerWorkflowStep
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSellerPortalScreen(
    currentSeller: SellerEntity?,
    shop: ShopEntity?,
    products: List<ProductEntity>,
    orders: List<OrderEntity>,
    onUpdateWorkflowStep: (SellerEntity, SellerWorkflowStep) -> Unit,
    onToggleEshop: (SellerEntity, String) -> Unit,
    onAddProduct: (sellerId: String, shopId: String, name: String, category: String, subcategory: String, brand: String, sku: String, desc: String, price: Double, stock: Int) -> Unit,
    onUpdateStock: (ProductEntity, Int) -> Unit,
    onSavePhoto: (sellerId: String, shopId: String, productId: String, uri: Uri, caption: String) -> Unit,
    onContactSupport: (name: String, email: String, category: String, subject: String, msg: String) -> Unit
) {
    if (currentSeller == null) {
        Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
            Text("No active seller associated with session. Please select Seller role or register a store.")
        }
        return
    }

    var selectedTab by remember { mutableStateOf(0) } // 0: Overview, 1: Products & Stock, 2: Orders, 3: Support
    val tabs = listOf("Overview & Status", "Products & Stock", "Orders (${orders.size})", "Support Desk")

    val sellerProducts = products.filter { it.sellerId == currentSeller.sellerId || (shop != null && it.shopId == shop.shopId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_seller_portal_screen")
    ) {
        // Seller Profile Header
        Surface(color = CorporateNavyDark) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(currentSeller.businessName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Category: ${currentSeller.businessCategory} • Ref: ${currentSeller.sellerId}", color = EmeraldTealLight, fontSize = 11.sp)
                    }
                    StatusBadge(status = currentSeller.sellerStatus)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Owner: ${currentSeller.ownerName} • ${currentSeller.district}", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    StatusBadge(status = "Shop: ${currentSeller.eshopStatus}")
                }
            }
        }

        // Sub Tabs
        TabRow(selectedTabIndex = selectedTab, containerColor = MaterialTheme.colorScheme.surface, contentColor = EmeraldTeal) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontSize = 11.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            when (selectedTab) {
                0 -> SellerOverviewTab(
                    seller = currentSeller,
                    shop = shop,
                    onToggleEshop = onToggleEshop,
                    onUpdateWorkflowStep = onUpdateWorkflowStep
                )
                1 -> SellerProductsTab(
                    seller = currentSeller,
                    shop = shop,
                    products = sellerProducts,
                    onAddProduct = onAddProduct,
                    onUpdateStock = onUpdateStock,
                    onSavePhoto = onSavePhoto
                )
                2 -> SellerOrdersTab(orders = orders)
                3 -> SellerSupportTab(seller = currentSeller, onSend = onContactSupport)
            }
        }
    }
}

@Composable
fun SellerOverviewTab(
    seller: SellerEntity,
    shop: ShopEntity?,
    onToggleEshop: (SellerEntity, String) -> Unit,
    onUpdateWorkflowStep: (SellerEntity, SellerWorkflowStep) -> Unit
) {
    val currentWorkflow = SellerWorkflowStep.fromString(seller.workflowStep)

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 11-Step Workflow Visualizer
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Seller Onboarding Workflow", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Step ${currentWorkflow.stepNumber} of 11", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFCCFBF1), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(currentWorkflow.label, fontWeight = FontWeight.Bold, color = EmeraldTealMuted, fontSize = 13.sp)
                        Text(currentWorkflow.description, color = Color(0xFF134E4A), fontSize = 11.sp)
                    }
                }

                // Workflow step stepper dropdown/chips
                Text("Transition Workflow State:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    SellerWorkflowStep.entries.forEach { step ->
                        FilterChip(
                            selected = currentWorkflow == step,
                            onClick = { onUpdateWorkflowStep(seller, step) },
                            label = { Text("${step.stepNumber}. ${step.label}", fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldTeal,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Shop Management Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("E-Shop Storefront Controls", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Storefront URL: worldmart.com/shops/${seller.businessName.lowercase().replace(" ", "-")}", fontSize = 11.sp, color = EmeraldTeal)
                Text(seller.storefrontDescription, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("E-Shop Status: ${seller.eshopStatus}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Button(
                        onClick = {
                            val next = if (seller.eshopStatus == "Live") "Paused" else "Live"
                            onToggleEshop(seller, next)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = if (seller.eshopStatus == "Live") Color(0xFFE2E8F0) else EmeraldTeal),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (seller.eshopStatus == "Live") "Pause E-Shop" else "Activate Live E-Shop", color = if (seller.eshopStatus == "Live") Color.Black else Color.White, fontSize = 11.sp)
                    }
                }
            }
        }

        // Metrics Summary
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Listed Products", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${seller.productCount} SKUs", fontSize = 18.sp, fontWeight = FontWeight.Black, color = EmeraldTeal)
                }
            }
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Monthly GMV", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$${String.format("%,.0f", seller.monthlyGmv)}", fontSize = 18.sp, fontWeight = FontWeight.Black, color = AmberGold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProductsTab(
    seller: SellerEntity,
    shop: ShopEntity?,
    products: List<ProductEntity>,
    onAddProduct: (sellerId: String, shopId: String, name: String, category: String, subcategory: String, brand: String, sku: String, desc: String, price: Double, stock: Int) -> Unit,
    onUpdateStock: (ProductEntity, Int) -> Unit,
    onSavePhoto: (sellerId: String, shopId: String, productId: String, uri: Uri, caption: String) -> Unit
) {
    var showAddProductDialog by remember { mutableStateOf(false) }
    var photoUploadProduct by remember { mutableStateOf<ProductEntity?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        val p = photoUploadProduct
        if (uri != null && p != null) {
            onSavePhoto(seller.sellerId, shop?.shopId ?: seller.shopId, p.productId, uri, "Seller Catalog Photo")
            photoUploadProduct = null
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Store Catalog (${products.size} Products)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Button(
                onClick = { showAddProductDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Product", fontSize = 11.sp)
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(products, key = { it.productId }) { prod ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(prod.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("SKU: ${prod.sku} • Brand: ${prod.brand}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Text("$${String.format("%.2f", prod.price)}", fontWeight = FontWeight.Black, fontSize = 14.sp, color = EmeraldTeal)
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("Stock: ${prod.stockQuantity}", fontSize = 11.sp)
                                IconButton(
                                    onClick = { onUpdateStock(prod, prod.stockQuantity + 5) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.AddCircleOutline, contentDescription = "Add Stock", tint = EmeraldTeal)
                                }
                            }

                            Button(
                                onClick = {
                                    photoUploadProduct = prod
                                    photoPickerLauncher.launch("image/*")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = CorporateNavyDark),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Upload Photo", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }

        if (showAddProductDialog) {
            AddProductDialog(
                sellerList = listOf(seller),
                onDismiss = { showAddProductDialog = false },
                onAdd = { sId, n, cat, scat, desc, pr, stk, photo ->
                    onAddProduct(seller.sellerId, shop?.shopId ?: seller.shopId, n, cat, scat, seller.businessName, "SKU-WM-${(1000..9999).random()}", desc, pr, stk)
                    showAddProductDialog = false
                }
            )
        }
    }
}

@Composable
fun SellerOrdersTab(orders: List<OrderEntity>) {
    if (orders.isEmpty()) {
        Text("No customer orders recorded yet.")
        return
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(orders, key = { it.orderId }) { order ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(order.orderId, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        StatusBadge(status = order.orderStatus)
                    }
                    Text("Customer: ${order.customerName} (${order.customerPhone})", fontSize = 11.sp)
                    Text("Items: ${order.itemsSummary}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Address: ${order.shippingAddress}, ${order.district}", fontSize = 10.sp, color = SlateTextMuted)
                        Text("$${String.format("%.2f", order.totalAmount)}", fontWeight = FontWeight.Black, color = EmeraldTeal)
                    }
                }
            }
        }
    }
}

@Composable
fun SellerSupportTab(
    seller: SellerEntity,
    onSend: (name: String, email: String, category: String, subject: String, msg: String) -> Unit
) {
    var subject by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }
    var sentMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Contact World Mart Merchant Support", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Direct line to your assigned field rep and merchant disputes escalation team.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { Text("Subject (e.g. Catalog assistance, Payout inquiry)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = msg, onValueChange = { msg = it }, label = { Text("Inquiry Details *") }, minLines = 4, modifier = Modifier.fillMaxWidth())

        if (sentMsg != null) {
            Text(sentMsg ?: "", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
            onClick = {
                if (msg.isNotBlank()) {
                    onSend(seller.ownerName, seller.contactEmail, "Vendor Support", subject.ifBlank { "Seller Support Request" }, msg)
                    sentMsg = "Support ticket dispatched. Assigned Rep: ${seller.assignedRepresentative} will contact you."
                    subject = ""
                    msg = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Merchant Ticket", fontWeight = FontWeight.Bold)
        }
    }
}
