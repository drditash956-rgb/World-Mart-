package com.example.ui.publicsite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import com.example.data.StaffEntity
import com.example.ui.components.DivisionBadge
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun PublicTeamScreen(
    staffList: List<StaffEntity>,
    selectedDivision: String?,
    onSelectDivision: (String?) -> Unit,
    onSelectEmployee: (StaffEntity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val divisions = listOf(
        "All",
        "Leadership",
        "Technology",
        "Marketing",
        "Finance",
        "Operations",
        "Business Development",
        "Customer Support"
    )

    val filteredStaff = staffList.filter { staff ->
        val matchesDivision = selectedDivision == null || selectedDivision == "All" ||
                staff.division.equals(selectedDivision, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                staff.fullName.contains(searchQuery, ignoreCase = true) ||
                staff.position.contains(searchQuery, ignoreCase = true) ||
                staff.department.contains(searchQuery, ignoreCase = true) ||
                staff.employeeId.contains(searchQuery, ignoreCase = true)
        matchesDivision && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("public_team_screen")
    ) {
        // Banner Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(CorporateNavyDark, CorporateNavySurface)
                    )
                )
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Column {
                Text(
                    text = "PEOPLE & TALENT",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "World Mart Leadership & Team",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dynamic corporate directory connected to our live operational database.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        // Search Bar & Division Filter
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by name, role, department or ID...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = EmeraldTeal)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("team_search_field"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EmeraldTeal,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Horizontal Division Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                divisions.forEach { div ->
                    val isSelected = (selectedDivision == div) || (selectedDivision == null && div == "All")
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectDivision(if (div == "All") null else div) },
                        label = {
                            Text(
                                text = div,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldTeal,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_division_$div")
                    )
                }
            }
        }

        // Staff Directory List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Team Members (${filteredStaff.size})",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Tap for full profile",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (filteredStaff.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No staff members found matching query.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                items(filteredStaff, key = { it.employeeId }) { staff ->
                    StaffDirectoryCard(
                        staff = staff,
                        onClick = { onSelectEmployee(staff) }
                    )
                }
            }
        }
    }
}

@Composable
fun StaffDirectoryCard(
    staff: StaffEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("staff_card_${staff.employeeId}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Stylized Monogram Avatar
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(EmeraldTeal, AmberGold)
                        )
                    )
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(CorporateNavyDark),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = staff.fullName.split(" ")
                            .mapNotNull { it.firstOrNull()?.toString() }
                            .take(2)
                            .joinToString(""),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = staff.fullName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = staff.employeeId,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldTeal
                    )
                }

                Text(
                    text = staff.position,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 2.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DivisionBadge(division = staff.division)
                    Text(
                        text = "• ${staff.location}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Profile",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
