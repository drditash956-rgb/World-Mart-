package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.StaffEntity
import com.example.ui.theme.*

@Composable
fun StaffProfileDialog(
    staff: StaffEntity,
    canViewConfidential: Boolean = false,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.88f)
                .testTag("staff_profile_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with Executive Gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(CorporateNavyDark, CorporateNavySurface, CorporateNavyLight)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = EmeraldTeal.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = staff.employeeId,
                                    color = EmeraldTealLight,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.testTag("close_profile_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Profile",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Professional Portrait Avatar
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(EmeraldTeal, AmberGold)
                                    )
                                )
                                .padding(3.dp),
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
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = staff.fullName,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = staff.position,
                            color = EmeraldTealLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DivisionBadge(division = staff.division)
                            StatusBadge(status = staff.employmentStatus)
                        }
                    }
                }

                // Profile Body Details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Quick Info Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProfileAttribute(
                            icon = Icons.Default.Apartment,
                            label = "Department",
                            value = staff.department,
                            modifier = Modifier.weight(1f)
                        )
                        ProfileAttribute(
                            icon = Icons.Default.Place,
                            label = "Location",
                            value = staff.location,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProfileAttribute(
                            icon = Icons.Default.DateRange,
                            label = "Joining Date",
                            value = staff.joiningDate,
                            modifier = Modifier.weight(1f)
                        )
                        ProfileAttribute(
                            icon = Icons.Default.Email,
                            label = "Official Work Email",
                            value = staff.officialWorkEmail,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProfileAttribute(
                            icon = Icons.Default.SupervisorAccount,
                            label = "Reporting Manager",
                            value = staff.manager,
                            modifier = Modifier.weight(1f)
                        )
                        ProfileAttribute(
                            icon = Icons.Default.VerifiedUser,
                            label = "Account Status",
                            value = staff.accountStatus,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

                    // Professional Biography
                    Text(
                        text = "Professional Biography",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = staff.professionalBiography,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Key Responsibilities
                    Text(
                        text = "Core Responsibilities",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = staff.responsibilities,
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Qualifications
                    Text(
                        text = "Qualifications & Background",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = AmberGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = staff.qualification,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Confidential Section (Only shown when canViewConfidential == true)
                    if (canViewConfidential) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFEF2F2)
                            ),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(Color(0xFFF87171), Color(0xFFDC2626)))),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Security Protected",
                                        tint = Color(0xFFB91C1C),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "CONFIDENTIAL INTERNAL RECORD (AUTHORIZED ONLY)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB91C1C)
                                    )
                                }
                                Text(
                                    text = "• Salary Grade: ${staff.salaryGrade}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF7F1D1D)
                                )
                                Text(
                                    text = "• Bank Account Disbursal: ${staff.bankAccountMasked}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF7F1D1D)
                                )
                                Text(
                                    text = "• National ID File: ${staff.nationalIdMasked}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF7F1D1D)
                                )
                                Text(
                                    text = "• Internal Notes: ${staff.internalNotes}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF7F1D1D)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("dismiss_profile_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = CorporateNavyDark)
                    ) {
                        Text("Close Profile", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileAttribute(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = EmeraldTeal,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
