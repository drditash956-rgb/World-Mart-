package com.example.ui.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.UserAccountEntity
import com.example.model.UserRole
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthDialog(
    currentUser: UserAccountEntity?,
    currentRole: UserRole,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onLogin: (username: String, password: String) -> Unit,
    onRegister: (username: String, password: String, email: String, fullName: String, role: UserRole, phone: String) -> Unit,
    onLogout: () -> Unit,
    onQuickSwitchRole: (UserRole) -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserRole.CUSTOMER) }
    var roleDropdownExpanded by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.92f)
                .testTag("auth_dialog_surface"),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "WORLD MART SECURITY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldTeal,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = if (currentUser != null) "Active Session Profile" else if (isRegisterMode) "Create User Account" else "Sign In to World Mart",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Active Session Info Card
                if (currentUser != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = currentUser.fullName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Surface(shape = RoundedCornerShape(6.dp), color = EmeraldTeal) {
                                    Text(text = currentRole.title, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                                }
                            }
                            Text(text = "Username: ${currentUser.username} • User ID: ${currentUser.userId}", color = EmeraldTealLight, fontSize = 11.sp)
                            Text(text = "Email: ${currentUser.email}", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            if (currentUser.linkedSellerId.isNotBlank()) {
                                Text(text = "Linked Seller: ${currentUser.linkedSellerId}", color = AmberGoldLight, fontSize = 11.sp)
                            }
                            if (currentUser.linkedEmployeeId.isNotBlank()) {
                                Text(text = "Linked Staff ID: ${currentUser.linkedEmployeeId}", color = AmberGoldLight, fontSize = 11.sp)
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Button(
                                onClick = onLogout,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth().height(38.dp).testTag("auth_logout_button")
                            ) {
                                Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Log Out (Switch to Guest)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Quick One-Tap Role Switcher for Auditing All 11 Roles!
                Text(
                    text = "Fast Role Switcher (For Audit & Verification)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Instantly switch authenticated sessions between any of the 11 World Mart roles:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UserRole.entries.forEach { role ->
                        val isCurrent = currentRole == role
                        FilterChip(
                            selected = isCurrent,
                            onClick = { onQuickSwitchRole(role) },
                            label = { Text(role.title, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = if (role.isSalesRep()) AmberGold else EmeraldTeal,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.testTag("quick_switch_role_${role.name}")
                        )
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

                // Real Sign In / Sign Up Forms
                if (currentUser == null) {
                    if (!isRegisterMode) {
                        // Login Form
                        Text("Real Database Authentication", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Default password for pre-configured accounts is: password123", fontSize = 11.sp, color = EmeraldTeal)

                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username or Email") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = EmeraldTeal) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("auth_login_username")
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldTeal) },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("auth_login_password")
                        )

                        if (errorMessage != null) {
                            Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onLogin(username, password) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("auth_login_submit_button")
                        ) {
                            Text("Sign In", fontWeight = FontWeight.Bold)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("New to World Mart? ", fontSize = 12.sp)
                            TextButton(onClick = { isRegisterMode = true }) {
                                Text("Register Account", fontWeight = FontWeight.Bold, color = EmeraldTeal)
                            }
                        }
                    } else {
                        // Registration Form
                        Text("Register Real User Account", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                        OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name *") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username *") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address *") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password *") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Role Selector
                        ExposedDropdownMenuBox(
                            expanded = roleDropdownExpanded,
                            onExpandedChange = { roleDropdownExpanded = !roleDropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedRole.title,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Select Account Role") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = roleDropdownExpanded) },
                                modifier = Modifier.fillMaxWidth().menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = roleDropdownExpanded,
                                onDismissRequest = { roleDropdownExpanded = false }
                            ) {
                                listOf(UserRole.CUSTOMER, UserRole.SELLER, UserRole.SALES_REP, UserRole.OPERATIONS, UserRole.MARKETING).forEach { r ->
                                    DropdownMenuItem(
                                        text = { Text(r.title) },
                                        onClick = {
                                            selectedRole = r
                                            roleDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        if (errorMessage != null) {
                            Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onRegister(username, password, email, fullName, selectedRole, phone) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("auth_register_submit_button")
                        ) {
                            Text("Create Account", fontWeight = FontWeight.Bold)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Already have an account? ", fontSize = 12.sp)
                            TextButton(onClick = { isRegisterMode = false }) {
                                Text("Sign In", fontWeight = FontWeight.Bold, color = EmeraldTeal)
                            }
                        }
                    }
                }
            }
        }
    }
}
