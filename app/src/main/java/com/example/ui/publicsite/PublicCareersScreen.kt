package com.example.ui.publicsite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.JobEntity
import com.example.ui.components.DivisionBadge
import com.example.ui.theme.*

@Composable
fun PublicCareersScreen(
    jobList: List<JobEntity>,
    onApply: (jobId: String, jobTitle: String, name: String, email: String, phone: String, qualifications: String, portfolio: String) -> Unit
) {
    var selectedJobForApply by remember { mutableStateOf<JobEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_careers_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(CorporateNavyDark, CorporateNavySurface)
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "JOIN OUR MISSION",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Careers at World Mart",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Build the technology and logistics connecting thousands of local community businesses.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        // Culture Perks
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Why Work at World Mart?", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CareerPerk(icon = Icons.Default.HealthAndSafety, title = "Comprehensive Healthcare", modifier = Modifier.weight(1f))
                    CareerPerk(icon = Icons.Default.MonetizationOn, title = "Equity & Bonus Packages", modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CareerPerk(icon = Icons.Default.WorkOutline, title = "Hybrid Flexibility", modifier = Modifier.weight(1f))
                    CareerPerk(icon = Icons.Default.School, title = "Annual Learning Stipend", modifier = Modifier.weight(1f))
                }
            }
        }

        // Open Positions Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Open Positions (${jobList.size})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        jobList.forEach { job ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.testTag("job_card_${job.jobId}")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = job.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        DivisionBadge(division = job.division)
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                            Text(text = job.location, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                            Text(text = job.employmentType, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                            Text(text = job.experienceLevel, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Text(
                        text = job.overview,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(text = "Requirements:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text(text = job.requirements, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
                    }

                    Button(
                        onClick = { selectedJobForApply = job },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .testTag("apply_button_${job.jobId}")
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Apply for Position", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        // Job Application Modal Dialog
        selectedJobForApply?.let { job ->
            JobApplicationDialog(
                job = job,
                onDismiss = { selectedJobForApply = null },
                onSubmit = { name, email, phone, quals, portfolio ->
                    onApply(job.jobId, job.title, name, email, phone, quals, portfolio)
                    selectedJobForApply = null
                }
            )
        }
    }
}

@Composable
fun CareerPerk(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(16.dp))
        Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CorporateNavyDark)
    }
}

@Composable
fun JobApplicationDialog(
    job: JobEntity,
    onDismiss: () -> Unit,
    onSubmit: (name: String, email: String, phone: String, quals: String, portfolio: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var qualifications by remember { mutableStateOf("") }
    var portfolio by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(text = "Apply: ${job.title}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Division: ${job.division} • Ref: ${job.jobId}", fontSize = 11.sp, color = EmeraldTeal)
            }
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
                    modifier = Modifier.fillMaxWidth().testTag("app_name_input")
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("app_email_input")
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Contact Phone *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("app_phone_input")
                )
                OutlinedTextField(
                    value = qualifications,
                    onValueChange = { qualifications = it },
                    label = { Text("Qualifications & Years of Exp *") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth().testTag("app_quals_input")
                )
                OutlinedTextField(
                    value = portfolio,
                    onValueChange = { portfolio = it },
                    label = { Text("LinkedIn / Portfolio / Resume URL") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("app_portfolio_input")
                )
                if (error != null) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || phone.isBlank() || qualifications.isBlank()) {
                        error = "Please fill in all mandatory fields"
                        return@Button
                    }
                    onSubmit(name, email, phone, qualifications, portfolio)
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                modifier = Modifier.testTag("submit_job_application_button")
            ) {
                Text("Submit Application", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
