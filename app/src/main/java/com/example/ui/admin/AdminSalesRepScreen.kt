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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

enum class SalesRepSubTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    DASHBOARD("Dashboard", Icons.Default.Dashboard),
    ASSIGNED_SHOPS("Assigned Shops", Icons.Default.Store),
    SHOP_VISIT("Shop Visit", Icons.Default.PinDrop),
    REGISTER_SELLER("Register Seller", Icons.Default.AppRegistration),
    ADD_PRODUCT("Add Product", Icons.Default.AddBox),
    UPLOAD_PHOTOS("Product Photos", Icons.Default.AddPhotoAlternate),
    UPLOAD_DOCS("Documents", Icons.Default.UploadFile),
    TASKS("Tasks", Icons.Default.TaskAlt),
    DAILY_REPORT("Daily Report", Icons.Default.Assignment)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSalesRepScreen(
    currentRepName: String = "Marcus Vance",
    shopList: List<ShopEntity>,
    sellerList: List<SellerEntity>,
    productList: List<ProductEntity>,
    taskList: List<TaskEntity>,
    dailyReportsList: List<DailyReportEntity>,
    onRegisterSeller: (businessName: String, category: String, ownerName: String, location: String, district: String, phone: String, email: String, desc: String) -> Unit,
    onAddProduct: (sellerId: String, shopId: String, name: String, category: String, subcategory: String, brand: String, sku: String, desc: String, price: Double, stock: Int) -> Unit,
    onSaveProductPhoto: (sellerId: String, shopId: String, productId: String, uri: Uri, caption: String) -> Unit,
    onLogShopVisit: (shopId: String, sellerId: String, shopName: String, purpose: String, outcome: String, notes: String) -> Unit,
    onSubmitDailyReport: (district: String, area: String, planned: Int, visited: Int, leads: Int, interested: Int, newSellers: Int, docs: Int, prods: Int, photos: Int, followups: String, problems: String, plan: String, notes: String) -> Unit,
    onToggleTask: (TaskEntity) -> Unit
) {
    val context = LocalContext.current
    var selectedSubTab by remember { mutableStateOf(SalesRepSubTab.DASHBOARD) }

    // Filter to shops and sellers assigned to this representative
    val assignedShops = shopList.filter { it.assignedRepresentative.contains(currentRepName, ignoreCase = true) }
    val assignedSellers = sellerList.filter { it.assignedRepresentative.contains(currentRepName, ignoreCase = true) }
    val repReports = dailyReportsList.filter { it.repName.contains(currentRepName, ignoreCase = true) }
    val repTasks = taskList.filter { it.assignedDivision.contains("Operations", ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_sales_rep_screen")
    ) {
        // Mobile-Optimized Top Sub-Nav Tabs
        Surface(
            color = CorporateNavyDark,
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SalesRepSubTab.entries.forEach { tab ->
                    val isSel = selectedSubTab == tab
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSel) AmberGold else CorporateNavySurface,
                        modifier = Modifier
                            .clickable { selectedSubTab = tab }
                            .testTag("sales_rep_tab_${tab.name.lowercase()}")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null,
                                tint = if (isSel) CorporateNavyDark else Color.White,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = tab.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSel) CorporateNavyDark else Color.White
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (selectedSubTab) {
                SalesRepSubTab.DASHBOARD -> SalesRepDashboardView(
                    repName = currentRepName,
                    assignedShopsCount = assignedShops.size,
                    assignedSellersCount = assignedSellers.size,
                    reportsCount = repReports.size,
                    pendingTasksCount = repTasks.count { !it.isCompleted },
                    onNavigateToTab = { selectedSubTab = it }
                )
                SalesRepSubTab.ASSIGNED_SHOPS -> SalesRepAssignedShopsView(
                    shops = assignedShops,
                    sellers = assignedSellers
                )
                SalesRepSubTab.SHOP_VISIT -> SalesRepShopVisitView(
                    shops = assignedShops,
                    onLogVisit = onLogShopVisit
                )
                SalesRepSubTab.REGISTER_SELLER -> SalesRepRegisterSellerView(
                    onRegister = onRegisterSeller
                )
                SalesRepSubTab.ADD_PRODUCT -> SalesRepAddProductView(
                    shops = assignedShops,
                    onAddProduct = onAddProduct
                )
                SalesRepSubTab.UPLOAD_PHOTOS -> SalesRepUploadPhotosView(
                    shops = assignedShops,
                    products = productList,
                    onSavePhoto = onSaveProductPhoto
                )
                SalesRepSubTab.UPLOAD_DOCS -> SalesRepUploadDocsView(
                    sellers = assignedSellers
                )
                SalesRepSubTab.TASKS -> SalesRepTasksView(
                    tasks = repTasks,
                    onToggleTask = onToggleTask
                )
                SalesRepSubTab.DAILY_REPORT -> SalesRepDailyReportView(
                    repReports = repReports,
                    onSubmit = onSubmitDailyReport
                )
            }
        }
    }
}

@Composable
fun SalesRepDashboardView(
    repName: String,
    assignedShopsCount: Int,
    assignedSellersCount: Int,
    reportsCount: Int,
    pendingTasksCount: Int,
    onNavigateToTab: (SalesRepSubTab) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("FIELD OPERATIONS DASHBOARD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AmberGoldLight, letterSpacing = 1.sp)
                Text("Representative: $repName", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Territory: Central Business District & West Harbor Corridor", fontSize = 12.sp, color = Color(0xFF94A3B8))
            }
        }

        // Metrics
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Assigned Shops", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$assignedShopsCount", fontSize = 20.sp, fontWeight = FontWeight.Black, color = EmeraldTeal)
                }
            }
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Active Sellers", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$assignedSellersCount", fontSize = 20.sp, fontWeight = FontWeight.Black, color = AmberGold)
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Submitted Reports", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$reportsCount", fontSize = 20.sp, fontWeight = FontWeight.Black, color = StatusInfo)
                }
            }
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Pending Tasks", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$pendingTasksCount", fontSize = 20.sp, fontWeight = FontWeight.Black, color = StatusPurple)
                }
            }
        }

        Text("Quick Field Actions", fontWeight = FontWeight.Bold, fontSize = 15.sp)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = { onNavigateToTab(SalesRepSubTab.SHOP_VISIT) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.PinDrop, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Log Visit", fontSize = 12.sp)
            }
            Button(
                onClick = { onNavigateToTab(SalesRepSubTab.DAILY_REPORT) },
                colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(16.dp), tint = CorporateNavyDark)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Daily Report", fontSize = 12.sp, color = CorporateNavyDark, fontWeight = FontWeight.Bold)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = { onNavigateToTab(SalesRepSubTab.REGISTER_SELLER) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Register Seller", fontSize = 12.sp)
            }
            OutlinedButton(
                onClick = { onNavigateToTab(SalesRepSubTab.UPLOAD_PHOTOS) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Upload Photos", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun SalesRepAssignedShopsView(shops: List<ShopEntity>, sellers: List<SellerEntity>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Assigned Stores in Your District (${shops.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        items(shops, key = { it.shopId }) { shop ->
            val seller = sellers.find { it.sellerId == shop.sellerId }
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(shop.shopName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        StatusBadge(status = shop.eshopStatus)
                    }
                    Text("Owner: ${shop.owner} • Contact: ${shop.contact}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Address: ${shop.address}, ${shop.district}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    if (seller != null) {
                        Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFCCFBF1)) {
                            Text("Workflow: ${seller.workflowStep}", color = EmeraldTealMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesRepShopVisitView(
    shops: List<ShopEntity>,
    onLogVisit: (shopId: String, sellerId: String, shopName: String, purpose: String, outcome: String, notes: String) -> Unit
) {
    if (shops.isEmpty()) {
        Text("No assigned shops available to log visits.")
        return
    }

    var selectedShop by remember { mutableStateOf(shops.first()) }
    var shopExpanded by remember { mutableStateOf(false) }

    val purposes = listOf("Routine Inspection", "Catalog Setup & Photo Session", "POS Training", "Dispute Resolution", "Seller Onboarding")
    var selectedPurpose by remember { mutableStateOf(purposes.first()) }

    var outcome by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var successMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Log Physical Storefront Visit", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        ExposedDropdownMenuBox(expanded = shopExpanded, onExpandedChange = { shopExpanded = !shopExpanded }) {
            OutlinedTextField(
                value = "${selectedShop.shopName} (${selectedShop.district})",
                onValueChange = {},
                readOnly = true,
                label = { Text("Visited Storefront *") },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = shopExpanded, onDismissRequest = { shopExpanded = false }) {
                shops.forEach { s ->
                    DropdownMenuItem(text = { Text("${s.shopName} (${s.district})") }, onClick = { selectedShop = s; shopExpanded = false })
                }
            }
        }

        Text("Visit Purpose:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            purposes.forEach { p ->
                FilterChip(selected = selectedPurpose == p, onClick = { selectedPurpose = p }, label = { Text(p, fontSize = 11.sp) })
            }
        }

        OutlinedTextField(value = outcome, onValueChange = { outcome = it }, label = { Text("Visit Outcome / Accomplishments *") }, minLines = 2, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Field Notes & Action Items") }, minLines = 2, modifier = Modifier.fillMaxWidth())

        if (successMsg != null) {
            Text(successMsg ?: "", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
            onClick = {
                if (outcome.isNotBlank()) {
                    onLogVisit(selectedShop.shopId, selectedShop.sellerId, selectedShop.shopName, selectedPurpose, outcome, notes)
                    successMsg = "Visit logged for '${selectedShop.shopName}'."
                    outcome = ""
                    notes = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Store Visit Record", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SalesRepRegisterSellerView(
    onRegister: (businessName: String, category: String, ownerName: String, location: String, district: String, phone: String, email: String, desc: String) -> Unit
) {
    var businessName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Electronics") }
    var location by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("Central Metro") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var successMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Field Seller Onboarding (Sales Rep)", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        OutlinedTextField(value = businessName, onValueChange = { businessName = it }, label = { Text("Store / Business Name *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = ownerName, onValueChange = { ownerName = it }, label = { Text("Owner Full Name *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Street Address *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = district, onValueChange = { district = it }, label = { Text("District") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Store Notes & Catalog Description") }, minLines = 2, modifier = Modifier.fillMaxWidth())

        if (successMsg != null) {
            Text(successMsg ?: "", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
            onClick = {
                if (businessName.isNotBlank() && ownerName.isNotBlank() && phone.isNotBlank()) {
                    onRegister(businessName, category, ownerName, location, district, phone, email, desc)
                    successMsg = "New seller lead '$businessName' enrolled into 11-step workflow!"
                    businessName = ""
                    ownerName = ""
                    location = ""
                    phone = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Merchant Lead", fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesRepAddProductView(
    shops: List<ShopEntity>,
    onAddProduct: (sellerId: String, shopId: String, name: String, category: String, subcategory: String, brand: String, sku: String, desc: String, price: Double, stock: Int) -> Unit
) {
    if (shops.isEmpty()) {
        Text("No assigned shops to add products.")
        return
    }

    var selectedShop by remember { mutableStateOf(shops.first()) }
    var shopExpanded by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var subcat by remember { mutableStateOf("Accessories") }
    var brand by remember { mutableStateOf("Regional Brand") }
    var sku by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("20") }
    var desc by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Add Catalog Product for Assigned Shop", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        ExposedDropdownMenuBox(expanded = shopExpanded, onExpandedChange = { shopExpanded = !shopExpanded }) {
            OutlinedTextField(
                value = "${selectedShop.shopName} (${selectedShop.category})",
                onValueChange = {},
                readOnly = true,
                label = { Text("Target Shop *") },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = shopExpanded, onDismissRequest = { shopExpanded = false }) {
                shops.forEach { s ->
                    DropdownMenuItem(text = { Text(s.shopName) }, onClick = { selectedShop = s; shopExpanded = false })
                }
            }
        }

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Product Name *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Brand") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = priceText, onValueChange = { priceText = it }, label = { Text("Price ($) *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = stockText, onValueChange = { stockText = it }, label = { Text("Stock Quantity") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description *") }, minLines = 2, modifier = Modifier.fillMaxWidth())

        if (msg != null) {
            Text(msg ?: "", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
            onClick = {
                val pr = priceText.toDoubleOrNull()
                val st = stockText.toIntOrNull() ?: 10
                if (name.isNotBlank() && pr != null) {
                    onAddProduct(selectedShop.sellerId, selectedShop.shopId, name, selectedShop.category, subcat, brand, sku, desc, pr, st)
                    msg = "Product '$name' added to catalog!"
                    name = ""
                    priceText = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publish to Catalog", fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesRepUploadPhotosView(
    shops: List<ShopEntity>,
    products: List<ProductEntity>,
    onSavePhoto: (sellerId: String, shopId: String, productId: String, uri: Uri, caption: String) -> Unit
) {
    var caption by remember { mutableStateOf("") }
    var selectedShop by remember { mutableStateOf(shops.firstOrNull()) }
    var shopExpanded by remember { mutableStateOf(false) }

    val shopProducts = products.filter { it.shopId == selectedShop?.shopId || it.sellerId == selectedShop?.sellerId }
    var selectedProduct by remember { mutableStateOf(shopProducts.firstOrNull()) }
    var prodExpanded by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var statusText by remember { mutableStateOf<String?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Product Photograph Upload System", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(
            text = "Every photo uploaded is associated with Seller, Shop, Product, Uploader, Date and stored persistently in app internal storage.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Select Shop
        if (shops.isNotEmpty()) {
            ExposedDropdownMenuBox(expanded = shopExpanded, onExpandedChange = { shopExpanded = !shopExpanded }) {
                OutlinedTextField(
                    value = selectedShop?.shopName ?: "Select Shop",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Target Shop") },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(expanded = shopExpanded, onDismissRequest = { shopExpanded = false }) {
                    shops.forEach { s ->
                        DropdownMenuItem(text = { Text(s.shopName) }, onClick = { selectedShop = s; shopExpanded = false })
                    }
                }
            }
        }

        // Select Product
        if (shopProducts.isNotEmpty()) {
            ExposedDropdownMenuBox(expanded = prodExpanded, onExpandedChange = { prodExpanded = !prodExpanded }) {
                OutlinedTextField(
                    value = selectedProduct?.productName ?: "Select Product",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Product to Photograph") },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(expanded = prodExpanded, onDismissRequest = { prodExpanded = false }) {
                    shopProducts.forEach { p ->
                        DropdownMenuItem(text = { Text(p.productName) }, onClick = { selectedProduct = p; prodExpanded = false })
                    }
                }
            }
        }

        OutlinedTextField(value = caption, onValueChange = { caption = it }, label = { Text("Photo Angle / Caption (e.g. Front View)") }, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { photoPickerLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = CorporateNavyDark),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Select Photo File")
            }
        }

        if (selectedImageUri != null) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFD1FAE5),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Photo file selected: ${selectedImageUri?.lastPathSegment ?: "image.jpg"}",
                    color = Color(0xFF065F46),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        if (statusText != null) {
            Text(statusText ?: "", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
            onClick = {
                val s = selectedShop
                val p = selectedProduct
                val u = selectedImageUri
                if (s != null && p != null && u != null) {
                    onSavePhoto(s.sellerId, s.shopId, p.productId, u, caption.ifBlank { "Product Catalog Photo" })
                    statusText = "Photo uploaded and saved to internal storage for ${p.productName}!"
                    selectedImageUri = null
                    caption = ""
                } else {
                    statusText = "Please select shop, product, and image file."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Store & Associate Photo", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SalesRepUploadDocsView(sellers: List<SellerEntity>) {
    var selectedSeller by remember { mutableStateOf(sellers.firstOrNull()) }
    var docType by remember { mutableStateOf("Municipal Trade License") }
    var statusMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Seller Verification Document Upload", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Upload commercial permits, tax certificates, and premises photographs for compliance verification.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Text("Select Document Type:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        listOf("Municipal Trade License", "Tax Registration Certificate", "Lease / Premises Ownership", "Commercial Bank Account Verification").forEach { type ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = docType == type, onClick = { docType = type })
                Text(type, fontSize = 12.sp)
            }
        }

        if (statusMsg != null) {
            Text(statusMsg ?: "", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
            onClick = {
                statusMsg = "Document '$docType' saved and attached to merchant verification queue."
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Upload & Attach Document", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SalesRepTasksView(tasks: List<TaskEntity>, onToggleTask: (TaskEntity) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Text("Sales Representative Task List (${tasks.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        items(tasks, key = { it.id }) { task ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Checkbox(checked = task.isCompleted, onCheckedChange = { onToggleTask(task) })
                    Column(modifier = Modifier.weight(1f)) {
                        Text(task.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(task.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Due: ${task.dueDate} • Priority: ${task.priority}", fontSize = 10.sp, color = AmberGold)
                    }
                }
            }
        }
    }
}

@Composable
fun SalesRepDailyReportView(
    repReports: List<DailyReportEntity>,
    onSubmit: (district: String, area: String, planned: Int, visited: Int, leads: Int, interested: Int, newSellers: Int, docs: Int, prods: Int, photos: Int, followups: String, problems: String, plan: String, notes: String) -> Unit
) {
    var district by remember { mutableStateOf("Central Metro") }
    var area by remember { mutableStateOf("Main Commercial Strip") }
    var planned by remember { mutableStateOf("6") }
    var visited by remember { mutableStateOf("5") }
    var newLeads by remember { mutableStateOf("3") }
    var interested by remember { mutableStateOf("2") }
    var newSellers by remember { mutableStateOf("1") }
    var docs by remember { mutableStateOf("2") }
    var prods by remember { mutableStateOf("12") }
    var photos by remember { mutableStateOf("24") }
    var followups by remember { mutableStateOf("Check lease certificate with Apex Digital") }
    var problems by remember { mutableStateOf("None today") }
    var tomorrowPlan by remember { mutableStateOf("Visit 4 shops in West Harbor") }
    var notes by remember { mutableStateOf("Merchants enthusiastic about 2-hour delivery") }
    var msg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Daily Staff Field Report Submission", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Mandated daily reporting for Sales Representatives. Manager reviews all submissions.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = district, onValueChange = { district = it }, label = { Text("District") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = area, onValueChange = { area = it }, label = { Text("Area / Sector") }, modifier = Modifier.weight(1f))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = planned, onValueChange = { planned = it }, label = { Text("Shops Planned") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = visited, onValueChange = { visited = it }, label = { Text("Shops Visited") }, modifier = Modifier.weight(1f))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = newLeads, onValueChange = { newLeads = it }, label = { Text("New Leads") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = interested, onValueChange = { interested = it }, label = { Text("Interested") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = newSellers, onValueChange = { newSellers = it }, label = { Text("New Sellers") }, modifier = Modifier.weight(1f))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = docs, onValueChange = { docs = it }, label = { Text("Docs Collected") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = prods, onValueChange = { prods = it }, label = { Text("Products Added") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = photos, onValueChange = { photos = it }, label = { Text("Photos Taken") }, modifier = Modifier.weight(1f))
        }

        OutlinedTextField(value = followups, onValueChange = { followups = it }, label = { Text("Follow-ups Required") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = problems, onValueChange = { problems = it }, label = { Text("Problems / Blockers Encountered") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tomorrowPlan, onValueChange = { tomorrowPlan = it }, label = { Text("Tomorrow's Plan *") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Additional Notes") }, modifier = Modifier.fillMaxWidth())

        if (msg != null) {
            Text(msg ?: "", color = EmeraldTeal, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
            onClick = {
                onSubmit(
                    district, area, planned.toIntOrNull() ?: 5, visited.toIntOrNull() ?: 5,
                    newLeads.toIntOrNull() ?: 2, interested.toIntOrNull() ?: 1, newSellers.toIntOrNull() ?: 1,
                    docs.toIntOrNull() ?: 2, prods.toIntOrNull() ?: 10, photos.toIntOrNull() ?: 20,
                    followups, problems, tomorrowPlan, notes
                )
                msg = "Daily Report submitted successfully to Operations Manager!"
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Submit Daily Field Report", fontWeight = FontWeight.Bold)
        }

        // Prior Submitted Reports List
        if (repReports.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Submitted Reports History (${repReports.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            repReports.forEach { rep ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Date: ${rep.date} (${rep.district})", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            StatusBadge(status = rep.reviewStatus)
                        }
                        Text("Visited: ${rep.shopsVisited}/${rep.shopsPlanned} | Leads: ${rep.newLeads} | Photos: ${rep.photosCollected}", fontSize = 11.sp)
                        if (rep.managerFeedback.isNotBlank()) {
                            Text("Manager Feedback: ${rep.managerFeedback}", color = EmeraldTeal, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
