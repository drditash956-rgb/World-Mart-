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
                        text = "Staff & Personnel Database",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Total Staff: ${staffList.size} (${staffList.count { it.accountStatus == "Active" }} Active)",
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
