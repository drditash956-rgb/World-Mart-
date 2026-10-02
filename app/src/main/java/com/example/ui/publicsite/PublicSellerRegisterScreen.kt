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
fun PublicSellerRegisterScreen(
    onRegisterSubmit: (
        businessName: String,
        category: String,
        ownerName: String,
        location: String,
        district: String,
        phone: String,
        email: String,
        description: String
    ) -> Unit
) {
    var businessName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var licenseNumber by remember { mutableStateOf("") }

    val categories = listOf("Electronics", "Fresh Groceries", "Fashion", "Home & Living", "Health & Wellness", "Automotive")
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    val districts = listOf("Central Metro", "West Harbor", "East Valley", "North Park", "South Tech Hub")
    var selectedDistrict by remember { mutableStateOf(districts.first()) }
    var districtDropdownExpanded by remember { mutableStateOf(false) }

    var agreeTerms by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var submittedStoreName by remember { mutableStateOf("") }
    var generatedId by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_seller_registration_screen"),
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
                    text = "OFFICIAL ONBOARDING PORTAL",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Seller Registration Form",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Apply to join the WORLD_MART_SELLERS verified merchant collection.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "Business Information",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                // Business Name
                OutlinedTextField(
                    value = businessName,
                    onValueChange = { businessName = it },
                    label = { Text("Business / Store Name *") },
                    leadingIcon = { Icon(Icons.Default.Store, contentDescription = null, tint = EmeraldTeal) },
                    modifier = Modifier.fillMaxWidth().testTag("reg_business_name_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                // Owner Name
                OutlinedTextField(
                    value = ownerName,
                    onValueChange = { ownerName = it },
                    label = { Text("Owner / Representative Full Name *") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = EmeraldTeal) },
                    modifier = Modifier.fillMaxWidth().testTag("reg_owner_name_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                // Category Selector
                ExposedDropdownMenuBox(
                    expanded = categoryDropdownExpanded,
                    onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Business Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                        leadingIcon = { Icon(Icons.Default.Category, contentDescription = null, tint = EmeraldTeal) },
                        modifier = Modifier.fillMaxWidth().menuAnchor().testTag("reg_category_selector"),
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

                // District Selector
                ExposedDropdownMenuBox(
                    expanded = districtDropdownExpanded,
                    onExpandedChange = { districtDropdownExpanded = !districtDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedDistrict,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Operational District") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtDropdownExpanded) },
                        leadingIcon = { Icon(Icons.Default.Map, contentDescription = null, tint = EmeraldTeal) },
                        modifier = Modifier.fillMaxWidth().menuAnchor().testTag("reg_district_selector"),
                        shape = RoundedCornerShape(10.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = districtDropdownExpanded,
                        onDismissRequest = { districtDropdownExpanded = false }
                    ) {
                        districts.forEach { dist ->
                            DropdownMenuItem(
                                text = { Text(dist) },
                                onClick = {
                                    selectedDistrict = dist
                                    districtDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Physical Address
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Physical Storefront Street Address *") },
                    leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = EmeraldTeal) },
                    modifier = Modifier.fillMaxWidth().testTag("reg_address_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                // Phone & Email
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = contactPhone,
                        onValueChange = { contactPhone = it },
                        label = { Text("Contact Phone *") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = EmeraldTeal) },
                        modifier = Modifier.weight(1f).testTag("reg_phone_input"),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = contactEmail,
                        onValueChange = { contactEmail = it },
                        label = { Text("Contact Email *") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldTeal) },
                        modifier = Modifier.weight(1f).testTag("reg_email_input"),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                // Trade License / Tax ID
                OutlinedTextField(
                    value = licenseNumber,
                    onValueChange = { licenseNumber = it },
                    label = { Text("Commercial Registration / Tax ID Number") },
                    placeholder = { Text("e.g. CR-88329-METRO") },
                    leadingIcon = { Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = EmeraldTeal) },
                    modifier = Modifier.fillMaxWidth().testTag("reg_license_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                // Store Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Storefront Description & Products Sold") },
                    placeholder = { Text("Tell us about your specialties and typical customer volume...") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth().testTag("reg_description_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                // Terms checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Checkbox(
                        checked = agreeTerms,
                        onCheckedChange = { agreeTerms = it },
                        colors = CheckboxDefaults.colors(checkedColor = EmeraldTeal),
                        modifier = Modifier.testTag("reg_terms_checkbox")
                    )
                    Text(
                        text = "I certify that all store information provided is authentic and agree to World Mart's standard merchant platform terms and physical verification visit.",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = {
                        if (businessName.isBlank() || ownerName.isBlank() || location.isBlank() || contactPhone.isBlank() || contactEmail.isBlank()) {
                            errorMessage = "Please complete all mandatory fields marked with *"
                            return@Button
                        }
                        if (!agreeTerms) {
                            errorMessage = "Please accept the merchant platform terms to continue."
                            return@Button
                        }
                        errorMessage = null
                        submittedStoreName = businessName
                        generatedId = "WM-SLR-${(1000..9999).random()}"
                        onRegisterSubmit(
                            businessName,
                            selectedCategory,
                            ownerName,
                            location,
                            selectedDistrict,
                            contactPhone,
                            contactEmail,
                            description.ifBlank { "Independent local store in $selectedDistrict specialized in $selectedCategory." }
                        )
                        showSuccessDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_seller_registration_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submit Seller Application", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        // Success Confirmation Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD1FAE5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(32.dp))
                    }
                },
                title = {
                    Text(
                        text = "Application Received!",
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Thank you, $ownerName! Your store '$submittedStoreName' has been entered into the WORLD_MART_SELLERS collection.",
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Assigned District: $selectedDistrict", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "Representative: Marcus Vance (Field Operations)", fontSize = 11.sp)
                                Text(text = "Status: Pending Review & Verification", fontSize = 11.sp, color = AmberGold)
                            }
                        }
                        Text(
                            text = "Our field operations team will contact you at $contactPhone within 24 hours to schedule the complimentary physical onboarding inspection.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            // Reset fields
                            businessName = ""
                            ownerName = ""
                            location = ""
                            contactPhone = ""
                            contactEmail = ""
                            description = ""
                            licenseNumber = ""
                            agreeTerms = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CorporateNavyDark)
                    ) {
                        Text("Done", color = Color.White)
                    }
                }
            )
        }
    }
}
