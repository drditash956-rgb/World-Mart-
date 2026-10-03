package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StaffEntity
import com.example.model.UserRole
import com.example.ui.components.DivisionBadge
import com.example.ui.components.StaffProfileDialog
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

data class ExpenseClaimItem(
    val id: String,
    val staffName: String,
    val title: String,
    val amount: String,
    val details: String,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStaffScreen(
    staffList: List<StaffEntity>,
    currentUserRole: UserRole,
    onAddStaff: (
        name: String, position: String, division: String, department: String,
        location: String, joiningDate: String, status: String, bio: String,
        responsibilities: String, qualification: String, email: String,
        manager: String, accountStatus: String,
        salaryGrade: String, bank: String, natId: String, notes: String
    ) -> Unit,
    onToggleStaffStatus: (StaffEntity, String) -> Unit,
    onDeleteStaff: (StaffEntity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedDivision by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedEmployeeForDetail by remember { mutableStateOf<StaffEntity?>(null) }
    var activeSubTab by remember { mutableStateOf(0) } // 0: Personnel, 1: Attendance, 2: Expenses, 3: Leaderboard

    // Sample Attendance State
    var showCheckInDialog by remember { mutableStateOf(false) }
    val attendanceLogs = remember {
        mutableStateListOf(
            Triple("STF-8801", "Rakesh Patnaik", "09:15 AM • Buxi Bazaar Market • Morning Shift"),
            Triple("STF-8802", "Priyanka Mishra", "09:30 AM • Choudhury Bazar • Field Inspection"),
            Triple("STF-8804", "Biswajit Mohanty", "09:45 AM • Cuttack Main HQ • Office Operations"),
            Triple("STF-8806", "Sita Rani Behera", "10:00 AM • Link Road Zone • Merchant Onboarding")
        )
    }

    // Sample Travel Claims State
    var showClaimDialog by remember { mutableStateOf(false) }
    val expenseClaims = remember {
        mutableStateListOf(
            ExpenseClaimItem("CLM-101", "Rakesh Patnaik", "Petrol Allowance", "₹180", "14 km • Buxi Bazaar Shop Visits", "Approved"),
            ExpenseClaimItem("CLM-102", "Priyanka Mishra", "Auto Rickshaw Fare", "₹120", "Choudhury Market Onboarding", "Approved"),
            ExpenseClaimItem("CLM-103", "Sita Rani Behera", "Merchant Meeting Tea", "₹95", "Link Road Store Owners", "Pending Review")
        )
    }

    val divisions = listOf("All", "Leadership", "Technology", "Marketing", "Finance", "Operations", "Business Development", "Customer Support")

    val filtered = staffList.filter { s ->
        val matchesDiv = selectedDivision == "All" || s.division.equals(selectedDivision, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                s.fullName.contains(searchQuery, ignoreCase = true) ||
                s.position.contains(searchQuery, ignoreCase = true) ||
                s.manager.contains(searchQuery, ignoreCase = true) ||
                s.employeeId.contains(searchQuery, ignoreCase = true)
        matchesDiv && matchesSearch
    }

    Box(modifier = Modifier.fillMaxSize().testTag("admin_staff_screen")) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
                        text = "Staff & Operations Workspace",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Total Personnel: ${staffList.size} (${staffList.count { it.accountStatus == "Active" }} Active On Duty)",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (currentUserRole.canAccessStaff()) {
                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("admin_add_staff_button")
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Staff", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Sub-Tabs for Operational Staff Tools
            TabRow(
                selectedTabIndex = activeSubTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = EmeraldTeal,
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
            ) {
                Tab(
                    selected = activeSubTab == 0,
                    onClick = { activeSubTab = 0 },
                    text = { Text("Directory", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeSubTab == 1,
                    onClick = { activeSubTab = 1 },
                    text = { Text("Attendance", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeSubTab == 2,
                    onClick = { activeSubTab = 2 },
                    text = { Text("TA/DA Claims", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeSubTab == 3,
                    onClick = { activeSubTab = 3 },
                    text = { Text("Leaderboard", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
            }

            // Tab 0: Directory View
            if (activeSubTab == 0) {
                // Search & Filter
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by name, role, manager, ID...", fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("admin_staff_search"),
                    shape = RoundedCornerShape(10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    divisions.forEach { div ->
                        FilterChip(
                            selected = selectedDivision == div,
                            onClick = { selectedDivision = div },
                            label = { Text(div, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldTeal,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                // Staff list
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(filtered, key = { it.employeeId }) { staff ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedEmployeeForDetail = staff }
                                .testTag("admin_staff_card_${staff.employeeId}"),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.linearGradient(listOf(EmeraldTeal, AmberGold))
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = staff.fullName.take(2).uppercase(),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = staff.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        StatusBadge(status = staff.accountStatus)
                                    }
                                    Text(text = staff.position, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = "Manager: ${staff.manager}", fontSize = 11.sp, color = AmberGold, fontWeight = FontWeight.Medium)
                                    Row(
                                        modifier = Modifier.padding(top = 2.dp),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        DivisionBadge(division = staff.division)
                                        Text(text = staff.officialWorkEmail, fontSize = 11.sp, color = SlateTextMuted)
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    if (currentUserRole.canAccessStaff()) {
                                        Button(
                                            onClick = {
                                                val newStatus = if (staff.accountStatus == "Active") "Inactive" else "Active"
                                                onToggleStaffStatus(staff, newStatus)
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (staff.accountStatus == "Active") Color(0xFFF1F5F9) else EmeraldTeal
                                            ),
                                            modifier = Modifier.height(28.dp).testTag("toggle_staff_status_${staff.employeeId}")
                                        ) {
                                            Text(
                                                text = if (staff.accountStatus == "Active") "Deactivate" else "Activate",
                                                fontSize = 9.sp,
                                                color = if (staff.accountStatus == "Active") SlateTextSecondary else Color.White
                                            )
                                        }
                                    }
                                    IconButton(
                                        onClick = { onDeleteStaff(staff) },
                                        modifier = Modifier.size(24.dp).testTag("delete_staff_${staff.employeeId}")
                                    ) {
                                        Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Tab 1: Shift Attendance & Duty Check-In
            if (activeSubTab == 1) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.AccessTime, contentDescription = null, tint = EmeraldTealLight)
                                Text("Live Shift Check-In", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }

                            Button(
                                onClick = { showCheckInDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Clock In Duty", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Text("Staff members check in with duty zone and location tag.", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                }

                Text("Today's Checked-In Personnel (${attendanceLogs.size}):", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(attendanceLogs) { log ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = EmeraldTeal)
                                    Column {
                                        Text(log.second, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(log.third, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                Surface(
                                    color = EmeraldTeal.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("ON DUTY", color = EmeraldTeal, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Tab 2: Travel & Expense Allowance (TA/DA Claims)
            if (activeSubTab == 2) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = AmberGoldLight)
                                Text("Travel & Field Allowance (TA/DA)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }

                            Button(
                                onClick = { showClaimDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.AddCard, contentDescription = null, modifier = Modifier.size(16.dp), tint = CorporateNavyDark)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Submit Claim", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CorporateNavyDark)
                            }
                        }

                        Text("Log fuel, rickshaw, and field visit expenses for manager approval.", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                }

                Text("Recent Field Expense Claims:", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(expenseClaims) { claim ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(claim.staffName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("${claim.title} • ${claim.details}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(claim.amount, fontWeight = FontWeight.Black, fontSize = 15.sp, color = EmeraldTeal)
                                    Surface(
                                        color = if (claim.status == "Approved") EmeraldTeal.copy(alpha = 0.15f) else AmberGold.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            claim.status,
                                            color = if (claim.status == "Approved") EmeraldTeal else AmberGold,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Tab 3: Onboarding Leaderboard & Targets
            if (activeSubTab == 3) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = AmberGoldLight)
                                Text("Monthly Sales & Onboarding Quota", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }

                            Text("Top Sales Representatives & Merchant Acquisition Leaders", color = Color(0xFF94A3B8), fontSize = 12.sp)

                            HorizontalDivider(color = Color(0xFF334155))

                            // Leader 1
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("🥇", fontSize = 20.sp)
                                    Column {
                                        Text("Rakesh Patnaik", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("18 Shops Onboarded • Buxi Bazaar", color = Color(0xFF94A3B8), fontSize = 11.sp)
                                    }
                                }
                                Text("₹3,600 Commission", color = AmberGoldLight, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            LinearProgressIndicator(
                                progress = { 0.90f },
                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                                color = EmeraldTeal,
                                trackColor = Color(0xFF334155)
                            )

                            // Leader 2
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("🥈", fontSize = 20.sp)
                                    Column {
                                        Text("Priyanka Mishra", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("14 Shops Onboarded • Choudhury Market", color = Color(0xFF94A3B8), fontSize = 11.sp)
                                    }
                                }
                                Text("₹2,800 Commission", color = AmberGoldLight, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            LinearProgressIndicator(
                                progress = { 0.70f },
                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                                color = EmeraldTeal,
                                trackColor = Color(0xFF334155)
                            )
                        }
                    }
                }
            }
        }

        // Dialogs
        if (showCheckInDialog) {
            AlertDialog(
                onDismissRequest = { showCheckInDialog = false },
                title = { Text("Clock In Duty Shift", fontWeight = FontWeight.Bold) },
                text = { Text("Log your active shift attendance with current field location tag (Cuttack District).") },
                confirmButton = {
                    Button(
                        onClick = {
                            attendanceLogs.add(Triple("STF-SELF", "Current Staff Member", "Just Now • Cuttack Field Zone • On Duty"))
                            showCheckInDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
                    ) {
                        Text("Confirm Check-In")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCheckInDialog = false }) { Text("Cancel") }
                }
            )
        }

        if (showClaimDialog) {
            AlertDialog(
                onDismissRequest = { showClaimDialog = false },
                title = { Text("Submit Travel Claim (TA/DA)", fontWeight = FontWeight.Bold) },
                text = { Text("Log travel expenses (fuel, auto, merchant tea) incurred during shop visits.") },
                confirmButton = {
                    Button(
                        onClick = {
                            expenseClaims.add(ExpenseClaimItem("CLM-NEW", "Current Staff", "Field Travel Allowance", "₹150", "10 km Shop Visits", "Approved"))
                            showClaimDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AmberGold)
                    ) {
                        Text("Submit Claim", color = CorporateNavyDark)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showClaimDialog = false }) { Text("Cancel") }
                }
            )
        }

        // Detail dialog with clearance level
        selectedEmployeeForDetail?.let { staff ->
            StaffProfileDialog(
                staff = staff,
                canViewConfidential = currentUserRole.canAccessConfidentialStaffData(),
                onDismiss = { selectedEmployeeForDetail = null }
            )
        }

        // Add Staff Member Dialog
        if (showAddDialog) {
            AddStaffDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { n, pos, div, dept, loc, jd, st, bio, resp, qual, em, mgr, accSt, sal, bnk, nid, nts ->
                    onAddStaff(n, pos, div, dept, loc, jd, st, bio, resp, qual, em, mgr, accSt, sal, bnk, nid, nts)
                    showAddDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStaffDialog(
    onDismiss: () -> Unit,
    onAdd: (
        name: String, position: String, division: String, department: String,
        location: String, joiningDate: String, status: String, bio: String,
        responsibilities: String, qualification: String, email: String,
        manager: String, accountStatus: String,
        salaryGrade: String, bank: String, natId: String, notes: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("World Mart HQ, Metropolis") }
    var joiningDate by remember { mutableStateOf("Oct 2026") }
    var employmentStatus by remember { mutableStateOf("Full-Time") }
    var manager by remember { mutableStateOf("Julian Vance (CEO)") }
    var accountStatus by remember { mutableStateOf("Active") }
    var bio by remember { mutableStateOf("") }
    var responsibilities by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var salaryGrade by remember { mutableStateOf("Tier-3 Specialist") }
    var bankAccount by remember { mutableStateOf("•••• 8820") }
    var nationalId by remember { mutableStateOf("NAT-•••-9901") }
    var internalNotes by remember { mutableStateOf("Clearance Level 2") }

    val divisions = listOf("Leadership", "Technology", "Marketing", "Finance", "Operations", "Business Development", "Customer Support")
    var selectedDivision by remember { mutableStateOf(divisions[1]) }
    var divisionExpanded by remember { mutableStateOf(false) }

    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Add New Staff Profile (Reusable Template)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_name")
                )
                OutlinedTextField(
                    value = position,
                    onValueChange = { position = it },
                    label = { Text("Position / Title *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_position")
                )

                // Division Selector
                ExposedDropdownMenuBox(
                    expanded = divisionExpanded,
                    onExpandedChange = { divisionExpanded = !divisionExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedDivision,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Central Division") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = divisionExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = divisionExpanded,
                        onDismissRequest = { divisionExpanded = false }
                    ) {
                        divisions.forEach { d ->
                            DropdownMenuItem(text = { Text(d) }, onClick = { selectedDivision = d; divisionExpanded = false })
                        }
                    }
                }

                OutlinedTextField(value = department, onValueChange = { department = it }, label = { Text("Department") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = manager, onValueChange = { manager = it }, label = { Text("Reporting Manager *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Official Work Email") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = qualification, onValueChange = { qualification = it }, label = { Text("Qualification & Background *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Professional Biography *") }, minLines = 2, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = responsibilities, onValueChange = { responsibilities = it }, label = { Text("Responsibilities") }, minLines = 2, modifier = Modifier.fillMaxWidth())

                // Confidential Fields
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFEF2F2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Confidential Internal Fields (Authorized Only):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB91C1C))
                        OutlinedTextField(value = salaryGrade, onValueChange = { salaryGrade = it }, label = { Text("Salary Grade") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = bankAccount, onValueChange = { bankAccount = it }, label = { Text("Bank Mask") }, modifier = Modifier.fillMaxWidth())
                    }
                }

                if (error != null) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank() || position.isBlank() || bio.isBlank() || qualification.isBlank()) {
                        error = "Please fill in all mandatory fields"
                        return@Button
                    }
                    onAdd(name, position, selectedDivision, department.ifBlank { "$selectedDivision Dept" },
                        location, joiningDate, employmentStatus, bio, responsibilities, qualification,
                        email, manager, accountStatus, salaryGrade, bankAccount, nationalId, internalNotes)
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                modifier = Modifier.testTag("confirm_add_staff_button")
            ) {
                Text("Create Staff Record", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
