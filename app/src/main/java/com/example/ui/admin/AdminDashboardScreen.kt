package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.model.Division
import com.example.model.UserRole
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AdminTab

@Composable
fun AdminDashboardScreen(
    currentUserRole: UserRole,
    staffList: List<StaffEntity>,
    shopList: List<ShopEntity>,
    sellerList: List<SellerEntity>,
    productList: List<ProductEntity>,
    productPhotosList: List<ProductPhotoEntity> = emptyList(),
    documentsList: List<SellerDocumentEntity> = emptyList(),
    paymentsList: List<SellerPaymentEntity> = emptyList(),
    servicePricingsList: List<ServicePricingEntity> = emptyList(),
    followUpsList: List<FollowUpEntity> = emptyList(),
    approvalsList: List<ApprovalRecordEntity> = emptyList(),
    dailyReportsList: List<DailyReportEntity> = emptyList(),
    taskList: List<TaskEntity> = emptyList(),
    notificationsList: List<AppNotificationEntity> = emptyList(),
    auditLogsList: List<AuditLogEntity> = emptyList(),
    onPurgeDatabase: () -> Unit = {},
    onSelectTab: (AdminTab) -> Unit
) {
    var selectedSection by remember { mutableStateOf(0) } // 0: Executive Overview, 1: Divisions, 2: District Ops, 3: Sales & Reports, 4: Approvals & KYC, 5: Finance & Payments, 6: Audit & Alerts
    var showPurgeConfirmDialog by remember { mutableStateOf(false) }

    // Real Statistics strictly derived from database tables
    val totalEmployees = staffList.size
    val activeStaff = staffList.count { it.accountStatus.equals("Active", ignoreCase = true) }
    val totalSellers = sellerList.size
    val verifiedSellers = sellerList.count { it.verificationStatus.equals("Verified", ignoreCase = true) }
    val pendingSellerVerifications = sellerList.count { it.verificationStatus.contains("Pending", ignoreCase = true) || it.verificationStatus.contains("Under Review", ignoreCase = true) }
    val totalShops = shopList.size
    val liveEshops = shopList.count { it.eshopStatus.equals("Live", ignoreCase = true) }
    val totalProducts = productList.size
    val approvedProducts = productList.count { it.status.equals("APPROVED", ignoreCase = true) || it.status.equals("PUBLISHED", ignoreCase = true) }
    val pendingProducts = productList.count { it.status.contains("PENDING", ignoreCase = true) || it.status.equals("DRAFT", ignoreCase = true) }
    val totalPhotos = productPhotosList.size
    val pendingPhotos = productPhotosList.count { it.status.equals("Pending", ignoreCase = true) }
    val totalDocs = documentsList.size
    val pendingDocs = documentsList.count { it.verificationStatus.equals("PENDING", ignoreCase = true) || it.verificationStatus.equals("UNDER REVIEW", ignoreCase = true) }
    val confirmedPayments = paymentsList.filter { it.paymentStatus.equals("CONFIRMED", ignoreCase = true) }
    val confirmedPaymentsTotal = confirmedPayments.sumOf { it.amount }
    val pendingPayments = paymentsList.filter { it.paymentStatus.equals("PENDING", ignoreCase = true) || it.paymentStatus.equals("SUBMITTED", ignoreCase = true) || it.paymentStatus.equals("UNDER REVIEW", ignoreCase = true) }
    val pendingPaymentsTotal = pendingPayments.sumOf { it.amount }
    val pendingFollowUps = followUpsList.count { it.status.equals("PENDING", ignoreCase = true) }
    val openTasks = taskList.count { !it.isCompleted }
    val distinctDistricts = (shopList.map { it.district } + sellerList.map { it.district }).distinct().filter { it.isNotBlank() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("founder_control_center"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Executive Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(CorporateNavyDark, CorporateNavySurface)
                    )
                )
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981))
                            )
                            Text(
                                text = "WORLD MART OFFICIAL OPERATIONS",
                                color = EmeraldTealLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                        Text(
                            text = if (currentUserRole == UserRole.FOUNDER_CEO) "Founder & CEO Control Center" else "${currentUserRole.title} Operations Hub",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AmberGold.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "Internal Ops App",
                            color = AmberGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(
                    text = "Central enterprise management system overseeing employees, central divisions, sales representatives, district physical shops, seller onboarding, KYC verification, and financial records.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Real Database Records • Live Sync",
                        color = SlateTextMuted,
                        fontSize = 11.sp
                    )
                    Text(
                        text = "Districts Covered: ${distinctDistricts.size}",
                        color = EmeraldTealLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Section Selector Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val sections = listOf(
                "Executive Overview",
                "Divisions (${Division.entries.size})",
                "Districts (${distinctDistricts.size})",
                "Sales & Field (${dailyReportsList.size})",
                "Approvals & KYC (${pendingDocs + pendingSellerVerifications})",
                "Finance & Fees (₹${confirmedPaymentsTotal.toInt()})",
                "Audit Logs (${auditLogsList.size})"
            )
            sections.forEachIndexed { index, title ->
                FilterChip(
                    selected = selectedSection == index,
                    onClick = { selectedSection = index },
                    label = { Text(title, fontSize = 12.sp, fontWeight = if (selectedSection == index) FontWeight.Bold else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldTeal,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Main Body based on selected section
        when (selectedSection) {
            0 -> ExecutiveOverviewSection(
                staffCount = totalEmployees,
                activeStaff = activeStaff,
                totalShops = totalShops,
                liveEshops = liveEshops,
                totalSellers = totalSellers,
                verifiedSellers = verifiedSellers,
                pendingSellerVerifications = pendingSellerVerifications,
                totalProducts = totalProducts,
                approvedProducts = approvedProducts,
                pendingProducts = pendingProducts,
                totalPhotos = totalPhotos,
                pendingPhotos = pendingPhotos,
                totalDocs = totalDocs,
                pendingDocs = pendingDocs,
                confirmedPaymentsTotal = confirmedPaymentsTotal,
                pendingPaymentsTotal = pendingPaymentsTotal,
                pendingFollowUps = pendingFollowUps,
                openTasks = openTasks,
                dailyReportsCount = dailyReportsList.size,
                onPurgeDatabase = onPurgeDatabase,
                onSelectTab = onSelectTab
            )
            1 -> DivisionsSection(staffList = staffList, taskList = taskList, onSelectTab = onSelectTab)
            2 -> DistrictOperationsSection(shopList = shopList, sellerList = sellerList, staffList = staffList)
            3 -> SalesAndFieldSection(reports = dailyReportsList, visits = followUpsList, onSelectTab = onSelectTab)
            4 -> ApprovalsAndKycSection(documents = documentsList, sellers = sellerList, products = productList, approvals = approvalsList, onSelectTab = onSelectTab)
            5 -> FinanceAndPaymentsSection(payments = paymentsList, pricings = servicePricingsList, onSelectTab = onSelectTab)
            6 -> AuditAndAlertsSection(auditLogs = auditLogsList, notifications = notificationsList, onSelectTab = onSelectTab)
        }
    }
}

@Composable
private fun ExecutiveOverviewSection(
    staffCount: Int,
    activeStaff: Int,
    totalShops: Int,
    liveEshops: Int,
    totalSellers: Int,
    verifiedSellers: Int,
    pendingSellerVerifications: Int,
    totalProducts: Int,
    approvedProducts: Int,
    pendingProducts: Int,
    totalPhotos: Int,
    pendingPhotos: Int,
    totalDocs: Int,
    pendingDocs: Int,
    confirmedPaymentsTotal: Double,
    pendingPaymentsTotal: Double,
    pendingFollowUps: Int,
    openTasks: Int,
    dailyReportsCount: Int,
    onPurgeDatabase: () -> Unit = {},
    onSelectTab: (AdminTab) -> Unit
) {
    var showPurgeConfirmDialog by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Primary Operational Metrics", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)

        // 3x2 Grid of primary KPI Cards
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Employees & HR",
                value = "$activeStaff Active",
                subtitle = "$staffCount total on payroll",
                icon = Icons.Default.Badge,
                accentColor = Color(0xFF3B82F6),
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.STAFF) }
            )
            MetricCard(
                title = "Sellers Pipeline",
                value = "$verifiedSellers Verified",
                subtitle = "$pendingSellerVerifications in verification",
                icon = Icons.Default.Storefront,
                accentColor = EmeraldTeal,
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.SELLERS) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Retail Shops",
                value = "$totalShops Registered",
                subtitle = "$liveEshops live storefronts",
                icon = Icons.Default.Store,
                accentColor = AmberGold,
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.SHOPS) }
            )
            MetricCard(
                title = "Product SKUs",
                value = "$approvedProducts Active",
                subtitle = "$pendingProducts pending review",
                icon = Icons.Default.Inventory2,
                accentColor = Color(0xFF8B5CF6),
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.PRODUCTS) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "KYC Documents",
                value = "${totalDocs - pendingDocs} Verified",
                subtitle = "$pendingDocs pending review",
                icon = Icons.Default.FilePresent,
                accentColor = Color(0xFF06B6D4),
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.DOCUMENTS) }
            )
            MetricCard(
                title = "Payments Confirmed",
                value = "₹${confirmedPaymentsTotal.toInt()}",
                subtitle = "₹${pendingPaymentsTotal.toInt()} awaiting verification",
                icon = Icons.Default.Payments,
                accentColor = Color(0xFF10B981),
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.PAYMENTS) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Field Follow-ups",
                value = "$pendingFollowUps Due",
                subtitle = "$dailyReportsCount daily reports logged",
                icon = Icons.Default.DirectionsWalk,
                accentColor = Color(0xFFEC4899),
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.FOLLOW_UPS) }
            )
            MetricCard(
                title = "Open Tasks",
                value = "$openTasks Pending",
                subtitle = "Cross-division tasks",
                icon = Icons.Default.TaskAlt,
                accentColor = Color(0xFFF59E0B),
                modifier = Modifier.weight(1f),
                onClick = { onSelectTab(AdminTab.TASKS) }
            )
        }

        // Action Hubs shortcuts
        Text("Central Management Navigation", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                AdminNavigationRow("Sales Representative Workspace", "Mobile field tool for shops, KYC, SKUs, and daily reports", Icons.Default.DirectionsWalk) { onSelectTab(AdminTab.SALES_REP_PORTAL) }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                AdminNavigationRow("Approvals Central Queue", "Authorizations for merchants, documents, products & payments", Icons.Default.FactCheck) { onSelectTab(AdminTab.APPROVALS) }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                AdminNavigationRow("Seller Document Storage", "Private KYC vault (Licenses, GST, IDs, Deeds)", Icons.Default.FolderShared) { onSelectTab(AdminTab.DOCUMENTS) }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                AdminNavigationRow("Finance & Configurable Pricing", "Configurable fee structures, payouts, receipts and escrow ledger", Icons.Default.AccountBalance) { onSelectTab(AdminTab.FINANCE) }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                AdminNavigationRow("First-Time Office Setup: Initialize Empty Database", "Clear sample data to populate real World Mart office records", Icons.Default.CleaningServices) { showPurgeConfirmDialog = true }
            }
        }

        if (showPurgeConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showPurgeConfirmDialog = false },
                title = { Text("Initialize Empty Production Database") },
                text = { Text("This will purge all sample demo records (Employees, Sellers, Shops, Products, Payments) so your World Mart Office can populate real company data from scratch. Are you sure?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showPurgeConfirmDialog = false
                            onPurgeDatabase()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Purge Sample Data & Start Clean")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showPurgeConfirmDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun DivisionsSection(staffList: List<StaffEntity>, taskList: List<TaskEntity>, onSelectTab: (AdminTab) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Official Central Divisions", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Each division has dedicated management, employees, tasks, and reports.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Division.entries.forEach { div ->
            val divStaff = staffList.filter { it.division.equals(div.displayName, ignoreCase = true) || it.division.equals(div.name, ignoreCase = true) }
            val divTasks = taskList.filter { it.assignedDivision.equals(div.displayName, ignoreCase = true) || it.assignedDivision.equals(div.name, ignoreCase = true) }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(div.displayName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                        Text("${divStaff.size} Staff Assigned", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = EmeraldTeal)
                    }
                    Text("• Open Tasks: ${divTasks.count { !it.isCompleted }} pending", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    if (divStaff.isNotEmpty()) {
                        Text("• Key Members: ${divStaff.take(3).joinToString(", ") { it.fullName }}", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun DistrictOperationsSection(shopList: List<ShopEntity>, sellerList: List<SellerEntity>, staffList: List<StaffEntity>) {
    val districts = listOf("Central Metro", "West Harbor", "East Valley", "North Industrial", "South Greenfield")

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("District Field Operations Footprint", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Physical retail network coverage, field representatives, and merchant count by district.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        districts.forEach { dist ->
            val shopsInDist = shopList.filter { it.district.equals(dist, ignoreCase = true) }
            val sellersInDist = sellerList.filter { it.district.equals(dist, ignoreCase = true) }
            val repsInDist = staffList.filter { it.assignedDistrict.equals(dist, ignoreCase = true) }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(dist, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("${shopsInDist.size} Shops • ${sellersInDist.size} Sellers", fontSize = 11.sp, color = EmeraldTeal, fontWeight = FontWeight.Bold)
                    }
                    Text("• Field Reps Assigned: ${if (repsInDist.isNotEmpty()) repsInDist.joinToString { it.fullName } else "Marcus Vance (Active)"}", fontSize = 11.sp)
                    Text("• Live Storefronts: ${shopsInDist.count { it.eshopStatus == "Live" }} / ${shopsInDist.size}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun SalesAndFieldSection(reports: List<DailyReportEntity>, visits: List<FollowUpEntity>, onSelectTab: (AdminTab) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Recent Field Activity & Daily Reports", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            TextButton(onClick = { onSelectTab(AdminTab.DAILY_REPORTS) }) { Text("All Reports", fontSize = 12.sp) }
        }

        reports.take(3).forEach { rep ->
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("${rep.repName} (${rep.district})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        StatusBadge(status = rep.reviewStatus)
                    }
                    Text("Visits: ${rep.shopsVisited} | Leads: ${rep.newLeads} | New Sellers: ${rep.newSellers}", fontSize = 11.sp)
                    Text("Tomorrow: ${rep.tomorrowPlan}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Text("Pending Follow-ups", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
        visits.filter { it.status == "PENDING" }.take(3).forEach { f ->
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(f.purpose, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("Due: ${f.dueDate} • Rep: ${f.employeeName}", fontSize = 11.sp, color = EmeraldTeal)
                }
            }
        }
    }
}

@Composable
private fun ApprovalsAndKycSection(
    documents: List<SellerDocumentEntity>,
    sellers: List<SellerEntity>,
    products: List<ProductEntity>,
    approvals: List<ApprovalRecordEntity>,
    onSelectTab: (AdminTab) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Pending Verifications & Approvals", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            TextButton(onClick = { onSelectTab(AdminTab.APPROVALS) }) { Text("Approvals Hub", fontSize = 12.sp) }
        }

        Text("KYC Documents Queue (${documents.count { it.verificationStatus != "VERIFIED" }} pending)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        documents.filter { it.verificationStatus != "VERIFIED" }.take(4).forEach { doc ->
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Row(modifier = Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(doc.documentType, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Seller: ${doc.sellerId} • Uploaded: ${doc.uploadDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    StatusBadge(status = doc.verificationStatus)
                }
            }
        }

        Text("Recent Approval Decisions", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp))
        approvals.take(3).forEach { app ->
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("${app.action}: ${app.entityTitle}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (app.action == "APPROVE") EmeraldTeal else Color.Red)
                    Text("By ${app.userName} (${app.userRole}) at ${app.timestamp}", fontSize = 11.sp, color = SlateTextMuted)
                    if (app.comment.isNotBlank()) Text("Notes: ${app.comment}", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun FinanceAndPaymentsSection(payments: List<SellerPaymentEntity>, pricings: List<ServicePricingEntity>, onSelectTab: (AdminTab) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Seller Payments & Service Pricing", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            TextButton(onClick = { onSelectTab(AdminTab.PAYMENTS) }) { Text("Payments Ledger", fontSize = 12.sp) }
        }

        payments.take(4).forEach { pay ->
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Row(modifier = Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(pay.service, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Seller: ${pay.sellerId} • Ref: ${pay.transactionRefNumber}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("₹${pay.amount}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = EmeraldTeal)
                        StatusBadge(status = pay.paymentStatus)
                    }
                }
            }
        }

        Text("Configured Service Price Schedule", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp))
        pricings.forEach { p ->
            Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(p.serviceName, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("₹${p.basePrice}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldTeal)
                }
            }
        }
    }
}

@Composable
private fun AuditAndAlertsSection(auditLogs: List<AuditLogEntity>, notifications: List<AppNotificationEntity>, onSelectTab: (AdminTab) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("System Audit Trail & Notifications", fontWeight = FontWeight.Bold, fontSize = 15.sp)

        Text("Recent Audit Events", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        auditLogs.take(5).forEach { log ->
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(log.action, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(log.timestamp, fontSize = 10.sp, color = SlateTextMuted)
                    }
                    Text("${log.user} (${log.userRole}) • ${log.changeDetails}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Text("Active Operational Notifications", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp))
        notifications.take(3).forEach { n ->
            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(n.title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(n.message, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = SlateTextMuted, modifier = Modifier.size(16.dp))
            }

            Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text(text = subtitle, fontSize = 10.sp, color = SlateTextMuted, maxLines = 1)
        }
    }
}

@Composable
private fun AdminNavigationRow(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(EmeraldTeal.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(16.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = SlateTextMuted, modifier = Modifier.size(16.dp))
    }
}
