package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.UserRole
import com.example.ui.admin.*
import com.example.ui.components.AuthDialog
import com.example.ui.components.DownloadShareAppDialog
import com.example.ui.components.StaffProfileDialog
import com.example.ui.components.WorldMartAppBar
import com.example.ui.opening.WorldMartOpeningFlow
import com.example.ui.publicsite.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AdminTab
import com.example.ui.viewmodel.PublicPage
import com.example.ui.viewmodel.WorldMartViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: WorldMartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                WorldMartApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldMartApp(viewModel: WorldMartViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val isAdminMode by viewModel.isAdminMode.collectAsStateWithLifecycle()
    val currentPublicPage by viewModel.currentPublicPage.collectAsStateWithLifecycle()
    val currentAdminTab by viewModel.currentAdminTab.collectAsStateWithLifecycle()
    val currentUserRole by viewModel.currentUserRole.collectAsStateWithLifecycle()

    val staffList by viewModel.staffList.collectAsStateWithLifecycle()
    val shopList by viewModel.shopList.collectAsStateWithLifecycle()
    val sellerList by viewModel.sellerList.collectAsStateWithLifecycle()
    val productList by viewModel.productList.collectAsStateWithLifecycle()
    val dailyReportsList by viewModel.dailyReportsList.collectAsStateWithLifecycle()
    val orderList by viewModel.orderList.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val authenticatedUser by viewModel.authenticatedUser.collectAsStateWithLifecycle()
    val authError by viewModel.authError.collectAsStateWithLifecycle()

    val announcements by viewModel.announcements.collectAsStateWithLifecycle()
    val publicAnnouncements by viewModel.publicAnnouncements.collectAsStateWithLifecycle()
    val taskList by viewModel.taskList.collectAsStateWithLifecycle()
    val contentBlocks by viewModel.contentBlocks.collectAsStateWithLifecycle()
    val jobList by viewModel.jobList.collectAsStateWithLifecycle()
    val financialRecords by viewModel.financialRecords.collectAsStateWithLifecycle()
    val productPhotosList by viewModel.productPhotosList.collectAsStateWithLifecycle()
    val sellerDocumentsList by viewModel.sellerDocumentsList.collectAsStateWithLifecycle()
    val sellerPaymentsList by viewModel.sellerPaymentsList.collectAsStateWithLifecycle()
    val servicePricingsList by viewModel.servicePricingsList.collectAsStateWithLifecycle()
    val followUpsList by viewModel.followUpsList.collectAsStateWithLifecycle()
    val approvalRecordsList by viewModel.approvalRecordsList.collectAsStateWithLifecycle()
    val auditLogsList by viewModel.auditLogsList.collectAsStateWithLifecycle()
    val notificationsList by viewModel.notificationsList.collectAsStateWithLifecycle()
    val shopVisitsList by viewModel.shopVisitsList.collectAsStateWithLifecycle()

    val selectedEmployee by viewModel.selectedEmployee.collectAsStateWithLifecycle()
    val teamDivisionFilter by viewModel.teamDivisionFilter.collectAsStateWithLifecycle()
    val statusNotification by viewModel.statusNotification.collectAsStateWithLifecycle()

    var showAuthDialog by remember { mutableStateOf(false) }
    var showOpeningFlow by remember { mutableStateOf(true) }
    var showDownloadShareDialog by remember { mutableStateOf(false) }

    // Handle back button
    BackHandler(enabled = drawerState.isOpen || (isAdminMode && currentAdminTab != AdminTab.DASHBOARD) || (!isAdminMode && currentPublicPage != PublicPage.HOME)) {
        if (drawerState.isOpen) {
            coroutineScope.launch { drawerState.close() }
        } else if (isAdminMode && currentAdminTab != AdminTab.DASHBOARD) {
            viewModel.selectAdminTab(AdminTab.DASHBOARD)
        } else if (!isAdminMode && currentPublicPage != PublicPage.HOME) {
            viewModel.navigateToPublicPage(PublicPage.HOME)
        }
    }

    // Trigger snackbar when notification appears
    LaunchedEffect(statusNotification) {
        statusNotification?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearNotification()
        }
    }

    val heroHeadline = contentBlocks.find { it.key == "hero_headline" }?.body
        ?: "Building a Connected Marketplace for Businesses and Customers."
    val heroSubtext = contentBlocks.find { it.key == "hero_subtext" }?.body
        ?: "Uniting neighborhood retailers, specialty stores, and local shoppers into one unified high-speed digital ecosystem."

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = CorporateNavyDark,
                drawerContentColor = Color.White,
                modifier = Modifier.width(310.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Drawer Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Brush.linearGradient(listOf(EmeraldTeal, AmberGold))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = "Logo",
                                tint = CorporateNavyDark,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("WORLD", color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("MART", color = EmeraldTealLight, fontWeight = FontWeight.Black, fontSize = 16.sp)
                            }
                            Text(
                                text = "Navigation Menu",
                                color = SlateTextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Portal Mode Switcher
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CorporateNavySurface,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(6.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.switchToAdminMode(false)
                                    coroutineScope.launch { drawerState.close() }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (!isAdminMode) EmeraldTeal else Color.Transparent
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(38.dp)
                            ) {
                                Text("Public Portal", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }

                            Button(
                                onClick = {
                                    viewModel.switchToAdminMode(true)
                                    coroutineScope.launch { drawerState.close() }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isAdminMode) AmberGold else Color.Transparent
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).height(38.dp)
                            ) {
                                Text("Admin System", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isAdminMode) CorporateNavyDark else Color.White)
                            }
                        }
                    }

                    HorizontalDivider(color = CorporateNavyLight)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Menu Items List
                    if (!isAdminMode) {
                        Text(
                            text = "PUBLIC CORPORATE PAGES",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldTealLight,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(start = 8.dp, bottom = 6.dp)
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(PublicPage.entries) { page ->
                                val isSelected = currentPublicPage == page
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) EmeraldTeal.copy(alpha = 0.25f) else Color.Transparent,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.navigateToPublicPage(page)
                                            coroutineScope.launch { drawerState.close() }
                                        }
                                        .testTag("nav_public_${page.name.lowercase()}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = when (page) {
                                                PublicPage.HOME -> Icons.Default.Home
                                                PublicPage.ABOUT -> Icons.Default.Business
                                                PublicPage.VISION_MISSION -> Icons.Default.Flag
                                                PublicPage.OUR_TEAM -> Icons.Default.Groups
                                                PublicPage.CENTRAL_DIVISIONS -> Icons.Default.Apartment
                                                PublicPage.SERVICES -> Icons.Default.Category
                                                PublicPage.BECOME_A_SELLER -> Icons.Default.Storefront
                                                PublicPage.SELLER_REGISTRATION -> Icons.Default.AppRegistration
                                                PublicPage.HOW_IT_WORKS -> Icons.Default.SyncAlt
                                                PublicPage.CAREERS -> Icons.Default.Work
                                                PublicPage.NEWS_UPDATES -> Icons.Default.Newspaper
                                                PublicPage.FAQ -> Icons.Default.Quiz
                                                PublicPage.CONTACT -> Icons.Default.ContactMail
                                            },
                                            contentDescription = null,
                                            tint = if (isSelected) EmeraldTealLight else Color(0xFF94A3B8),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = page.title,
                                            fontSize = 13.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) Color.White else Color(0xFFCBD5E1)
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "ADMIN & STAFF MODULES",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmberGoldLight,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(start = 8.dp, bottom = 6.dp)
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(AdminTab.entries) { tab ->
                                val isSelected = currentAdminTab == tab
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) AmberGold.copy(alpha = 0.25f) else Color.Transparent,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.selectAdminTab(tab)
                                            coroutineScope.launch { drawerState.close() }
                                        }
                                        .testTag("nav_admin_${tab.name.lowercase()}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = when (tab) {
                                                AdminTab.DASHBOARD -> Icons.Default.Dashboard
                                                AdminTab.STAFF -> Icons.Default.Badge
                                                AdminTab.DIVISIONS -> Icons.Default.Apartment
                                                AdminTab.DISTRICTS -> Icons.Default.Map
                                                AdminTab.SHOPS -> Icons.Default.Store
                                                AdminTab.SELLERS -> Icons.Default.Storefront
                                                AdminTab.DOCUMENTS -> Icons.Default.FolderShared
                                                AdminTab.PAYMENTS -> Icons.Default.Payments
                                                AdminTab.PRODUCTS -> Icons.Default.Inventory2
                                                AdminTab.PRODUCT_PHOTOS -> Icons.Default.AddPhotoAlternate
                                                AdminTab.SALES_REP_PORTAL -> Icons.Default.DirectionsWalk
                                                AdminTab.FOLLOW_UPS -> Icons.Default.PinDrop
                                                AdminTab.DAILY_REPORTS -> Icons.Default.Description
                                                AdminTab.APPROVALS -> Icons.Default.FactCheck
                                                AdminTab.FINANCE -> Icons.Default.AccountBalance
                                                AdminTab.TASKS -> Icons.Default.TaskAlt
                                                AdminTab.NOTIFICATIONS -> Icons.Default.Notifications
                                                AdminTab.AUDIT_LOGS -> Icons.Default.ManageHistory
                                                AdminTab.CMS -> Icons.Default.Web
                                            },
                                            contentDescription = null,
                                            tint = if (isSelected) AmberGoldLight else Color(0xFF94A3B8),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = tab.title,
                                            fontSize = 13.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) Color.White else Color(0xFFCBD5E1)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = CorporateNavyLight)

                    // Footer with Active Account / Login
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showAuthDialog = true
                                coroutineScope.launch { drawerState.close() }
                            }
                            .padding(top = 10.dp)
                            .testTag("drawer_auth_button"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(CorporateNavySurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = EmeraldTealLight, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(authenticatedUser?.fullName ?: currentUserRole.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(
                                    text = if (authenticatedUser != null) "${currentUserRole.title} • Tap to switch/logout" else "Tap to Login / Register",
                                    fontSize = 10.sp,
                                    color = SlateTextMuted
                                )
                            }
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = SlateTextMuted, modifier = Modifier.size(16.dp))
                    }

                    // Download & Share App Drawer Button
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF0066FF),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .clickable {
                                coroutineScope.launch { drawerState.close() }
                                showDownloadShareDialog = true
                            }
                            .testTag("drawer_download_share_btn")
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.GetApp, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("📲 Download / Share App", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                WorldMartAppBar(
                    isAdminMode = isAdminMode,
                    currentUserRole = currentUserRole,
                    onToggleMode = { viewModel.switchToAdminMode(it) },
                    onRoleChange = { viewModel.switchUserRole(it) },
                    onOpenNavDrawer = { coroutineScope.launch { drawerState.open() } },
                    onOpenDownloadShareDialog = { showDownloadShareDialog = true }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                // Bottom Quick Navigation Bar
                Surface(
                    color = CorporateNavyDark,
                    tonalElevation = 8.dp,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    if (!isAdminMode) {
                        NavigationBar(
                            containerColor = CorporateNavyDark,
                            contentColor = Color.White
                        ) {
                            NavigationBarItem(
                                selected = currentPublicPage == PublicPage.HOME,
                                onClick = { viewModel.navigateToPublicPage(PublicPage.HOME) },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                label = { Text("Home", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = EmeraldTealLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = EmeraldTealLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentPublicPage == PublicPage.OUR_TEAM,
                                onClick = { viewModel.navigateToPublicPage(PublicPage.OUR_TEAM) },
                                icon = { Icon(Icons.Default.Groups, contentDescription = "Team") },
                                label = { Text("Team", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = EmeraldTealLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = EmeraldTealLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentPublicPage == PublicPage.BECOME_A_SELLER,
                                onClick = { viewModel.navigateToPublicPage(PublicPage.BECOME_A_SELLER) },
                                icon = { Icon(Icons.Default.Storefront, contentDescription = "Sellers") },
                                label = { Text("Sellers", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = EmeraldTealLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = EmeraldTealLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentPublicPage == PublicPage.HOW_IT_WORKS,
                                onClick = { viewModel.navigateToPublicPage(PublicPage.HOW_IT_WORKS) },
                                icon = { Icon(Icons.Default.SyncAlt, contentDescription = "How It Works") },
                                label = { Text("How", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = EmeraldTealLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = EmeraldTealLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentPublicPage == PublicPage.CONTACT,
                                onClick = { viewModel.navigateToPublicPage(PublicPage.CONTACT) },
                                icon = { Icon(Icons.Default.ContactMail, contentDescription = "Contact") },
                                label = { Text("Contact", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = EmeraldTealLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = EmeraldTealLight
                                )
                            )
                        }
                    } else {
                        NavigationBar(
                            containerColor = CorporateNavyDark,
                            contentColor = Color.White
                        ) {
                            NavigationBarItem(
                                selected = currentAdminTab == AdminTab.DASHBOARD,
                                onClick = { viewModel.selectAdminTab(AdminTab.DASHBOARD) },
                                icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                                label = { Text("Overview", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = AmberGoldLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = AmberGoldLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentAdminTab == AdminTab.STAFF,
                                onClick = { viewModel.selectAdminTab(AdminTab.STAFF) },
                                icon = { Icon(Icons.Default.Badge, contentDescription = "Staff") },
                                label = { Text("Staff", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = AmberGoldLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = AmberGoldLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentAdminTab == AdminTab.SELLERS,
                                onClick = { viewModel.selectAdminTab(AdminTab.SELLERS) },
                                icon = { Icon(Icons.Default.Storefront, contentDescription = "Sellers") },
                                label = { Text("Sellers", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = AmberGoldLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = AmberGoldLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentAdminTab == AdminTab.PRODUCTS,
                                onClick = { viewModel.selectAdminTab(AdminTab.PRODUCTS) },
                                icon = { Icon(Icons.Default.Inventory2, contentDescription = "Products") },
                                label = { Text("Products", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = AmberGoldLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = AmberGoldLight
                                )
                            )
                            NavigationBarItem(
                                selected = currentAdminTab == AdminTab.CMS,
                                onClick = { viewModel.selectAdminTab(AdminTab.CMS) },
                                icon = { Icon(Icons.Default.Web, contentDescription = "CMS") },
                                label = { Text("CMS", fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CorporateNavyDark,
                                    indicatorColor = AmberGoldLight,
                                    unselectedIconColor = Color(0xFF94A3B8),
                                    unselectedTextColor = Color(0xFF94A3B8),
                                    selectedTextColor = AmberGoldLight
                                )
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (!isAdminMode) {
                    when (currentPublicPage) {
                        PublicPage.HOME -> PublicHomeScreen(
                            heroHeadline = heroHeadline,
                            heroSubtext = heroSubtext,
                            onNavigate = { viewModel.navigateToPublicPage(it) },
                            onOpenDownloadShareDialog = { showDownloadShareDialog = true }
                        )
                        PublicPage.ABOUT -> PublicAboutScreen()
                        PublicPage.VISION_MISSION -> PublicVisionMissionScreen()
                        PublicPage.OUR_TEAM -> PublicTeamScreen(
                            staffList = staffList,
                            selectedDivision = teamDivisionFilter,
                            onSelectDivision = { viewModel.setTeamDivisionFilter(it) },
                            onSelectEmployee = { viewModel.setSelectedEmployee(it) }
                        )
                        PublicPage.CENTRAL_DIVISIONS -> PublicDivisionsScreen()
                        PublicPage.SERVICES -> PublicServicesScreen()
                        PublicPage.BECOME_A_SELLER -> PublicBecomeSellerScreen(
                            onNavigateToRegistration = { viewModel.navigateToPublicPage(PublicPage.SELLER_REGISTRATION) }
                        )
                        PublicPage.SELLER_REGISTRATION -> PublicSellerRegisterScreen(
                            onRegisterSubmit = { bn, cat, on, loc, dist, ph, em, desc ->
                                viewModel.registerSeller(bn, cat, on, loc, dist, ph, em, desc)
                            }
                        )
                        PublicPage.HOW_IT_WORKS -> PublicHowItWorksScreen()
                        PublicPage.CAREERS -> PublicCareersScreen(
                            jobList = jobList,
                            onApply = { jId, jTitle, n, em, ph, quals, port ->
                                viewModel.submitJobApplication(jId, jTitle, n, em, ph, quals, port)
                            }
                        )
                        PublicPage.NEWS_UPDATES -> PublicNewsScreen(newsList = publicAnnouncements)
                        PublicPage.FAQ -> PublicFaqScreen()
                        PublicPage.CONTACT -> PublicContactScreen(
                            onSubmitInquiry = { n, em, cat, sub, msg ->
                                viewModel.submitContactInquiry(n, em, cat, sub, msg)
                            }
                        )
                    }
                } else {
                    when (currentAdminTab) {
                        AdminTab.DASHBOARD -> AdminDashboardScreen(
                            currentUserRole = currentUserRole,
                            staffList = staffList,
                            shopList = shopList,
                            sellerList = sellerList,
                            productList = productList,
                            productPhotosList = productPhotosList,
                            documentsList = sellerDocumentsList,
                            paymentsList = sellerPaymentsList,
                            servicePricingsList = servicePricingsList,
                            followUpsList = followUpsList,
                            approvalsList = approvalRecordsList,
                            dailyReportsList = dailyReportsList,
                            taskList = taskList,
                            notificationsList = notificationsList,
                            auditLogsList = auditLogsList,
                            onPurgeDatabase = { viewModel.purgeOperationalData() },
                            onSelectTab = { viewModel.selectAdminTab(it) }
                        )
                        AdminTab.STAFF -> AdminStaffScreen(
                            staffList = staffList,
                            currentUserRole = currentUserRole,
                            onAddStaff = { n, pos, div, dept, loc, jd, st, bio, resp, qual, em, mgr, accSt, sal, bnk, nid, nts ->
                                viewModel.addNewStaff(n, pos, div, dept, loc, jd, st, bio, resp, qual, em, mgr, accSt, sal, bnk, nid, nts)
                            },
                            onToggleStaffStatus = { staff, newStatus ->
                                viewModel.updateStaffStatus(staff, newStatus)
                            },
                            onDeleteStaff = { viewModel.deleteStaff(it) }
                        )
                        AdminTab.DIVISIONS -> AdminDashboardScreen(
                            currentUserRole = currentUserRole,
                            staffList = staffList,
                            shopList = shopList,
                            sellerList = sellerList,
                            productList = productList,
                            productPhotosList = productPhotosList,
                            documentsList = sellerDocumentsList,
                            paymentsList = sellerPaymentsList,
                            servicePricingsList = servicePricingsList,
                            followUpsList = followUpsList,
                            approvalsList = approvalRecordsList,
                            dailyReportsList = dailyReportsList,
                            taskList = taskList,
                            notificationsList = notificationsList,
                            auditLogsList = auditLogsList,
                            onSelectTab = { viewModel.selectAdminTab(it) }
                        )
                        AdminTab.DISTRICTS -> AdminDashboardScreen(
                            currentUserRole = currentUserRole,
                            staffList = staffList,
                            shopList = shopList,
                            sellerList = sellerList,
                            productList = productList,
                            productPhotosList = productPhotosList,
                            documentsList = sellerDocumentsList,
                            paymentsList = sellerPaymentsList,
                            servicePricingsList = servicePricingsList,
                            followUpsList = followUpsList,
                            approvalsList = approvalRecordsList,
                            dailyReportsList = dailyReportsList,
                            taskList = taskList,
                            notificationsList = notificationsList,
                            auditLogsList = auditLogsList,
                            onSelectTab = { viewModel.selectAdminTab(it) }
                        )
                        AdminTab.SHOPS -> AdminShopScreen(
                            sellerList = sellerList,
                            shopList = shopList,
                            onToggleShopStatus = { seller, status ->
                                viewModel.updateSellerStatus(seller, newEshopStatus = status)
                            },
                            onAddShop = { name, owner, cat, addr, dist, blk, city, phone, rep ->
                                viewModel.addShop(name, owner, cat, addr, dist, blk, city, phone, rep)
                            },
                            onTogglePhysicalShopStatus = { shop, status ->
                                viewModel.updateShop(shop.copy(status = status))
                            }
                        )
                        AdminTab.SELLERS -> AdminSellerScreen(
                            sellerList = sellerList,
                            currentUserRole = currentUserRole,
                            onUpdateStatus = { seller, sSt, vSt, eSt, rep ->
                                viewModel.updateSellerStatus(seller, sSt, vSt, eSt, rep)
                            },
                            onAddSeller = { bn, cat, on, loc, dist, ph, em, desc, rep ->
                                viewModel.registerSeller(bn, cat, on, loc, dist, ph, em, desc, rep)
                            },
                            onDeleteSeller = { viewModel.updateSellerStatus(it, newSellerStatus = "Suspended") }
                        )
                        AdminTab.DOCUMENTS -> AdminDocumentsScreen(
                            documents = sellerDocumentsList,
                            sellerList = sellerList,
                            currentUserRole = currentUserRole,
                            onUploadDocument = { sId, dType, uri ->
                                viewModel.uploadSellerDocument(sId, dType, uri)
                            },
                            onVerifyDocument = { doc, status, reason ->
                                viewModel.verifyDocument(doc, status, reason)
                            }
                        )
                        AdminTab.PAYMENTS -> AdminPaymentsScreen(
                            payments = sellerPaymentsList,
                            pricings = servicePricingsList,
                            sellerList = sellerList,
                            shopList = shopList,
                            currentUserRole = currentUserRole,
                            onRecordPayment = { sId, shId, srv, amt, mth, ref, rUri, nts ->
                                viewModel.recordSellerPayment(sId, shId, srv, amt, mth, ref, rUri, nts)
                            },
                            onVerifyPayment = { pay, status, nts ->
                                viewModel.verifyPayment(pay, status, nts)
                            },
                            onUpdatePricing = { viewModel.updateServicePricing(it) },
                            onAddPricing = { n, c, pr, d, ds -> viewModel.addServicePricing(n, c, pr, d, ds) }
                        )
                        AdminTab.PRODUCTS -> AdminProductScreen(
                            productList = productList,
                            sellerList = sellerList,
                            onAddProduct = { sId, n, cat, scat, desc, pr, stk, photo ->
                                viewModel.addProduct(
                                    sellerId = sId,
                                    productName = n,
                                    category = cat,
                                    subcategory = scat,
                                    description = desc,
                                    price = pr,
                                    stock = stk,
                                    photoCode = photo
                                )
                            },
                            onUpdateStatus = { prod, nSt, avail ->
                                viewModel.updateProductStatus(prod, nSt, avail)
                            },
                            onDeleteProduct = { viewModel.updateProductStatus(it, "Archived", "Out of Stock") }
                        )
                        AdminTab.PRODUCT_PHOTOS -> AdminSalesRepScreen(
                            currentRepName = authenticatedUser?.fullName ?: "Marcus Vance",
                            shopList = shopList,
                            sellerList = sellerList,
                            productList = productList,
                            taskList = taskList,
                            dailyReportsList = dailyReportsList,
                            onRegisterSeller = { bn, cat, on, loc, dist, ph, em, desc ->
                                viewModel.registerSeller(bn, cat, on, loc, dist, ph, em, desc, assignedRep = authenticatedUser?.fullName ?: "Marcus Vance")
                            },
                            onAddProduct = { sId, shId, n, cat, scat, b, sku, desc, pr, stk ->
                                viewModel.addProduct(
                                    sellerId = sId,
                                    shopId = shId,
                                    productName = n,
                                    category = cat,
                                    subcategory = scat,
                                    brand = b,
                                    sku = sku,
                                    description = desc,
                                    price = pr,
                                    stock = stk
                                )
                            },
                            onSaveProductPhoto = { sId, shId, pId, uri, cap ->
                                viewModel.saveProductPhoto(sId, shId, pId, uri, cap)
                            },
                            onLogShopVisit = { shId, sId, shName, purp, outc, notes ->
                                viewModel.logShopVisit(shId, sId, shName, purp, outc, notes)
                            },
                            onSubmitDailyReport = { dist, area, pl, vis, leads, inter, newS, docs, prods, photos, fol, prob, plan, notes ->
                                viewModel.submitDailyReport(
                                    district = dist,
                                    area = area,
                                    shopsPlanned = pl,
                                    shopsVisited = vis,
                                    newLeads = leads,
                                    interestedShops = inter,
                                    newSellers = newS,
                                    documentsCollected = docs,
                                    paymentsSubmitted = 0.0,
                                    productsCollected = prods,
                                    photosCollected = photos,
                                    followUps = fol,
                                    problems = prob,
                                    tomorrowPlan = plan,
                                    notes = notes
                                )
                            },
                            onToggleTask = { viewModel.toggleTaskCompletion(it) }
                        )
                        AdminTab.SALES_REP_PORTAL -> AdminSalesRepScreen(
                            currentRepName = authenticatedUser?.fullName ?: "Marcus Vance",
                            shopList = shopList,
                            sellerList = sellerList,
                            productList = productList,
                            taskList = taskList,
                            dailyReportsList = dailyReportsList,
                            onRegisterSeller = { bn, cat, on, loc, dist, ph, em, desc ->
                                viewModel.registerSeller(bn, cat, on, loc, dist, ph, em, desc, assignedRep = authenticatedUser?.fullName ?: "Marcus Vance")
                            },
                            onAddProduct = { sId, shId, n, cat, scat, b, sku, desc, pr, stk ->
                                viewModel.addProduct(
                                    sellerId = sId,
                                    shopId = shId,
                                    productName = n,
                                    category = cat,
                                    subcategory = scat,
                                    brand = b,
                                    sku = sku,
                                    description = desc,
                                    price = pr,
                                    stock = stk
                                )
                            },
                            onSaveProductPhoto = { sId, shId, pId, uri, cap ->
                                viewModel.saveProductPhoto(sId, shId, pId, uri, cap)
                            },
                            onLogShopVisit = { shId, sId, shName, purp, outc, notes ->
                                viewModel.logShopVisit(shId, sId, shName, purp, outc, notes)
                            },
                            onSubmitDailyReport = { dist, area, pl, vis, leads, inter, newS, docs, prods, photos, fol, prob, plan, notes ->
                                viewModel.submitDailyReport(
                                    district = dist,
                                    area = area,
                                    shopsPlanned = pl,
                                    shopsVisited = vis,
                                    newLeads = leads,
                                    interestedShops = inter,
                                    newSellers = newS,
                                    documentsCollected = docs,
                                    paymentsSubmitted = 0.0,
                                    productsCollected = prods,
                                    photosCollected = photos,
                                    followUps = fol,
                                    problems = prob,
                                    tomorrowPlan = plan,
                                    notes = notes
                                )
                            },
                            onToggleTask = { viewModel.toggleTaskCompletion(it) }
                        )
                        AdminTab.FOLLOW_UPS -> AdminSalesRepScreen(
                            currentRepName = authenticatedUser?.fullName ?: "Marcus Vance",
                            shopList = shopList,
                            sellerList = sellerList,
                            productList = productList,
                            taskList = taskList,
                            dailyReportsList = dailyReportsList,
                            onRegisterSeller = { bn, cat, on, loc, dist, ph, em, desc ->
                                viewModel.registerSeller(bn, cat, on, loc, dist, ph, em, desc, assignedRep = authenticatedUser?.fullName ?: "Marcus Vance")
                            },
                            onAddProduct = { sId, shId, n, cat, scat, b, sku, desc, pr, stk ->
                                viewModel.addProduct(
                                    sellerId = sId,
                                    shopId = shId,
                                    productName = n,
                                    category = cat,
                                    subcategory = scat,
                                    brand = b,
                                    sku = sku,
                                    description = desc,
                                    price = pr,
                                    stock = stk
                                )
                            },
                            onSaveProductPhoto = { sId, shId, pId, uri, cap ->
                                viewModel.saveProductPhoto(sId, shId, pId, uri, cap)
                            },
                            onLogShopVisit = { shId, sId, shName, purp, outc, notes ->
                                viewModel.logShopVisit(shId, sId, shName, purp, outc, notes)
                            },
                            onSubmitDailyReport = { dist, area, pl, vis, leads, inter, newS, docs, prods, photos, fol, prob, plan, notes ->
                                viewModel.submitDailyReport(
                                    district = dist,
                                    area = area,
                                    shopsPlanned = pl,
                                    shopsVisited = vis,
                                    newLeads = leads,
                                    interestedShops = inter,
                                    newSellers = newS,
                                    documentsCollected = docs,
                                    paymentsSubmitted = 0.0,
                                    productsCollected = prods,
                                    photosCollected = photos,
                                    followUps = fol,
                                    problems = prob,
                                    tomorrowPlan = plan,
                                    notes = notes
                                )
                            },
                            onToggleTask = { viewModel.toggleTaskCompletion(it) }
                        )
                        AdminTab.DAILY_REPORTS -> AdminDailyReportsScreen(
                            reports = dailyReportsList,
                            currentUserRole = currentUserRole,
                            onReviewReport = { rep, status, feedback ->
                                viewModel.reviewDailyReport(rep, status, feedback)
                            }
                        )
                        AdminTab.APPROVALS -> AdminApprovalsScreen(
                            approvalsList = approvalRecordsList,
                            sellers = sellerList,
                            documents = sellerDocumentsList,
                            products = productList,
                            payments = sellerPaymentsList,
                            currentUserRole = currentUserRole,
                            onProcessApproval = { eType, eId, eTitle, act, pSt, nSt, comm ->
                                viewModel.processApproval(eType, eId, eTitle, act, pSt, nSt, comm)
                            }
                        )
                        AdminTab.FINANCE -> AdminFinanceScreen(
                            currentUserRole = currentUserRole,
                            financialRecords = financialRecords,
                            sellerList = sellerList
                        )
                        AdminTab.TASKS -> AdminTasksScreen(
                            taskList = taskList,
                            onToggleTask = { viewModel.toggleTaskCompletion(it) },
                            onAddTask = { t, d, div, pr, due ->
                                viewModel.addTask(t, d, div, pr, due)
                            },
                            onDeleteTask = { viewModel.toggleTaskCompletion(it) }
                        )
                        AdminTab.NOTIFICATIONS -> AdminDashboardScreen(
                            currentUserRole = currentUserRole,
                            staffList = staffList,
                            shopList = shopList,
                            sellerList = sellerList,
                            productList = productList,
                            productPhotosList = productPhotosList,
                            documentsList = sellerDocumentsList,
                            paymentsList = sellerPaymentsList,
                            servicePricingsList = servicePricingsList,
                            followUpsList = followUpsList,
                            approvalsList = approvalRecordsList,
                            dailyReportsList = dailyReportsList,
                            taskList = taskList,
                            notificationsList = notificationsList,
                            auditLogsList = auditLogsList,
                            onSelectTab = { viewModel.selectAdminTab(it) }
                        )
                        AdminTab.AUDIT_LOGS -> AdminApprovalsScreen(
                            approvalsList = approvalRecordsList,
                            sellers = sellerList,
                            documents = sellerDocumentsList,
                            products = productList,
                            payments = sellerPaymentsList,
                            currentUserRole = currentUserRole,
                            onProcessApproval = { eType, eId, eTitle, act, pSt, nSt, comm ->
                                viewModel.processApproval(eType, eId, eTitle, act, pSt, nSt, comm)
                            }
                        )
                        AdminTab.CMS -> AdminCmsScreen(
                            currentUserRole = currentUserRole,
                            contentBlocks = contentBlocks,
                            announcements = announcements,
                            onUpdateContent = { k, t, s, b ->
                                viewModel.updateContentBlock(k, t, s, b)
                            },
                            onAddAnnouncement = { t, c, s, cnt, pub ->
                                viewModel.postAnnouncement(t, c, s, cnt, pub)
                            }
                        )
                    }
                }

                // Selected employee profile dialog (if opened from any public or admin view)
                selectedEmployee?.let { staff ->
                    StaffProfileDialog(
                        staff = staff,
                        canViewConfidential = isAdminMode && currentUserRole.canAccessConfidentialStaffData(),
                        onDismiss = { viewModel.setSelectedEmployee(null) }
                    )
                }

                // Authentication and User Profile Dialog
                if (showAuthDialog) {
                    AuthDialog(
                        currentUser = authenticatedUser,
                        currentRole = currentUserRole,
                        errorMessage = authError,
                        onDismiss = {
                            showAuthDialog = false
                            viewModel.clearAuthError()
                        },
                        onLogin = { u, p ->
                            viewModel.login(u, p)
                        },
                        onRegister = { u, p, em, fn, r, ph ->
                            viewModel.register(u, p, em, fn, r, ph)
                        },
                        onLogout = {
                            viewModel.logout()
                            showOpeningFlow = true
                        },
                        onQuickSwitchRole = {
                            viewModel.switchUserRole(it)
                        }
                    )
                }

                // Download & Share App Dialog
                if (showDownloadShareDialog) {
                    DownloadShareAppDialog(
                        onDismiss = { showDownloadShareDialog = false }
                    )
                }

                // 3 Opening App Pages Overlay (Splash -> Onboarding -> Sign Up / Login)
                if (showOpeningFlow) {
                    WorldMartOpeningFlow(
                        onFlowComplete = { isReg, u, p, fn, em, mob, r ->
                            if (isReg) {
                                val userRole = try {
                                    UserRole.valueOf(r)
                                } catch (e: Exception) {
                                    UserRole.CUSTOMER
                                }
                                viewModel.register(u, p, em, fn, userRole, mob)
                            } else {
                                viewModel.login(u, p)
                            }
                            showOpeningFlow = false
                        },
                        onNavigateToLoginDirect = {
                            showOpeningFlow = false
                            showAuthDialog = true
                        }
                    )
                }
            }
        }
    }
}
