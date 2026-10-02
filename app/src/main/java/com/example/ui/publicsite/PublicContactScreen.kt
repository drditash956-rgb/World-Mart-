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
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicContactScreen(
    onSubmitInquiry: (name: String, email: String, category: String, subject: String, message: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val categories = listOf("General Inquiry", "Investor Relations", "Partnership & Enterprise", "Press & Media", "Vendor Support")
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    var submittedMessage by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_contact_screen"),
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
                    text = "GLOBAL & DISTRICT TOUCHPOINTS",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Contact World Mart",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Our executive team, field dispatch captains, and support specialists are here to connect.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        // Headquarters & Regional Hubs
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Corporate Headquarters & Regional Centers",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                ContactHubInfo(
                    title = "World Mart Corporate HQ",
                    address = "100 Innovation Boulevard, Floor 18, Metropolis Central",
                    contact = "hq@worldmart.com • +1 (800) 555-WMART"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                ContactHubInfo(
                    title = "District Fulfillment & Operations Hub",
                    address = "420 Harbor Logistics Way, Building C, West Harbor",
                    contact = "ops.dispatch@worldmart.com • +1 (800) 555-9627"
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                ContactHubInfo(
                    title = "Merchant Success & Verification Desk",
                    address = "75 Commercial Square, Suite 400, Central Metro",
                    contact = "sellers@worldmart.com • +1 (800) 555-7355"
                )
            }
        }

        // Direct Corporate Inquiry Form
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Send Direct Corporate Inquiry",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name *") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = EmeraldTeal) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("inquiry_name_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address *") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldTeal) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("inquiry_email_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                // Category selector
                ExposedDropdownMenuBox(
                    expanded = categoryDropdownExpanded,
                    onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Inquiry Department") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                        leadingIcon = { Icon(Icons.Default.Category, contentDescription = null, tint = EmeraldTeal) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = categoryDropdownExpanded,
                        onDismissRequest = { categoryDropdownExpanded = false }
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    selectedCategory = cat
                                    categoryDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject *") },
                    leadingIcon = { Icon(Icons.Default.Subject, contentDescription = null, tint = EmeraldTeal) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("inquiry_subject_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Your Message *") },
                    minLines = 4,
                    modifier = Modifier.fillMaxWidth().testTag("inquiry_message_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                if (error != null) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                if (submittedMessage != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFD1FAE5),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldTeal)
                            Text(text = submittedMessage ?: "", color = Color(0xFF065F46), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Button(
                    onClick = {
                        if (name.isBlank() || email.isBlank() || subject.isBlank() || message.isBlank()) {
                            error = "Please fill in all required fields."
                            return@Button
                        }
                        error = null
                        onSubmitInquiry(name, email, selectedCategory, subject, message)
                        submittedMessage = "Inquiry logged. Ref: WM-INQ-${(100..999).random()}. Response will be sent to $email."
                        name = ""
                        email = ""
                        subject = ""
                        message = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_inquiry_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submit Corporate Inquiry", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ContactHubInfo(title: String, address: String, contact: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
        Text(text = address, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = contact, fontSize = 11.sp, color = EmeraldTeal, fontWeight = FontWeight.SemiBold)
    }
}
