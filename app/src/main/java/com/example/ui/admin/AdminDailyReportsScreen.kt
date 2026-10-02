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
import com.example.data.DailyReportEntity
import com.example.model.UserRole
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDailyReportsScreen(
    reports: List<DailyReportEntity>,
    currentUserRole: UserRole,
    onReviewReport: (report: DailyReportEntity, newStatus: String, feedback: String) -> Unit
) {
    var reviewingReport by remember { mutableStateOf<DailyReportEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_daily_reports_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column {
            Text(
                text = "Daily Field Staff Reports",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Operations manager review portal for sales representatives (${reports.size} reports logged)",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(reports, key = { it.reportId }) { rep ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                            Column {
                                Text("Report #${rep.reportId} • ${rep.repName}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Date: ${rep.date} • ${rep.district} (${rep.area})", fontSize = 11.sp, color = EmeraldTeal)
                            }
                            StatusBadge(status = rep.reviewStatus)
                        }

                        // Statistics breakdown
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("• Visits: ${rep.shopsVisited} completed / ${rep.shopsPlanned} planned", fontSize = 11.sp)
                                Text("• Merchant Acquisition: ${rep.newLeads} leads, ${rep.interestedShops} interested, ${rep.newSellers} registered", fontSize = 11.sp)
                                Text("• Field Assets: ${rep.documentsCollected} docs collected, ${rep.productsCollected} SKUs added, ${rep.photosCollected} photos taken", fontSize = 11.sp)
                                if (rep.followUps.isNotBlank()) Text("• Follow-ups: ${rep.followUps}", fontSize = 11.sp)
                                if (rep.problems.isNotBlank()) Text("• Blockers: ${rep.problems}", fontSize = 11.sp, color = AmberGold)
                                Text("• Tomorrow's Plan: ${rep.tomorrowPlan}", fontSize = 11.sp)
                            }
                        }

                        if (rep.managerFeedback.isNotBlank()) {
                            Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFD1FAE5), modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Manager Review (${rep.managerReviewer}): ${rep.managerFeedback}",
                                    color = Color(0xFF065F46),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        if (currentUserRole.canReviewReports()) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Button(
                                    onClick = { reviewingReport = rep },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Icon(Icons.Default.RateReview, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Review & Feedback", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Manager Review Dialog
        reviewingReport?.let { report ->
            var status by remember { mutableStateOf("Reviewed") }
            var feedback by remember { mutableStateOf(report.managerFeedback) }

            AlertDialog(
                onDismissRequest = { reviewingReport = null },
                title = { Text("Review Report #${report.reportId}", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Representative: ${report.repName}", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Reviewed", "Action Required").forEach { s ->
                                FilterChip(
                                    selected = status == s,
                                    onClick = { status = s },
                                    label = { Text(s, fontSize = 11.sp) }
                                )
                            }
                        }
                        OutlinedTextField(
                            value = feedback,
                            onValueChange = { feedback = it },
                            label = { Text("Manager Feedback & Instructions *") },
                            minLines = 3,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onReviewReport(report, status, feedback)
                            reviewingReport = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
                    ) {
                        Text("Save Review")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { reviewingReport = null }) { Text("Cancel") }
                }
            )
        }
    }
}
