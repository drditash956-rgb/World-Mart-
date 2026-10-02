package com.example.ui.opening

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WorldMartLogoBadge
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CorporateNavyDark
import com.example.ui.theme.EmeraldTeal

/**
 * High-Fidelity recreation of the 3 Opening Screens from Image 1:
 * Page 1: Splash Screen / Welcome (Deep blue globe, logistics artwork, Get Started button)
 * Page 2: Onboarding (Buy Global, Sell Worldwide, Explore, Grow, Skip option)
 * Page 3: Sign Up / Login (Create Account, Social logins, Full Name, Email, Mobile, Sign Up)
 */
@Composable
fun WorldMartOpeningFlow(
    onFlowComplete: (isRegister: Boolean, username: String, pass: String, fullName: String, email: String, mobile: String, role: String) -> Unit,
    onNavigateToLoginDirect: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(1) } // 1: Splash, 2: Onboarding, 3: Sign Up / Login

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020B1C))
    ) {
        Crossfade(targetState = currentPage, label = "OpeningFlowTransition") { page ->
            when (page) {
                1 -> WorldMartSplashScreen(
                    onGetStarted = { currentPage = 2 }
                )
                2 -> WorldMartOnboardingScreen(
                    onSkip = { currentPage = 3 },
                    onGetStarted = { currentPage = 3 },
                    onGoToLogin = { currentPage = 3 }
                )
                3 -> WorldMartSignUpLoginScreen(
                    onSubmit = { isReg, u, p, fn, em, mob, r ->
                        onFlowComplete(isReg, u, p, fn, em, mob, r)
                    }
                )
            }
        }
    }
}

/**
 * Screen 1: Splash Screen / Welcome
 * - Deep gradient blue background.
 * - Globe logo with shopping cart.
 * - "WorldMart™" text & tagline: "Every Product. Every Country. Every Business. Everyone."
 * - Global trade/logistics illustration.
 * - Blue pill button: "Get Started ➔".
 * - Page dots (Dot 1 active).
 */
@Composable
fun WorldMartSplashScreen(
    onGetStarted: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF051937),
                        Color(0xFF004D7A),
                        Color(0xFF008793),
                        Color(0xFF0052CC)
                    )
                )
            )
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Status bar spacer
            Spacer(modifier = Modifier.height(24.dp))

            // Logo & Brand Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                WorldMartLogoBadge(size = 200.dp, showTaglineAndBadges = false)

                Text(
                    text = "WorldMart™",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "Every Product. Every Country.\nEvery Business. Everyone.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFE2E8F0),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            // Central Logistics & Trade Graphic Representation
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White.copy(alpha = 0.12f),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FeatureChipIcon(icon = Icons.Default.Flight, label = "Air Cargo")
                        FeatureChipIcon(icon = Icons.Default.DirectionsBoat, label = "Ocean Freight")
                        FeatureChipIcon(icon = Icons.Default.LocalShipping, label = "Local Express")
                    }

                    Text(
                        text = "Connecting local sellers in Odisha & India with buyers worldwide.",
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Bottom CTA & Page Indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
            ) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(54.dp)
                        .testTag("splash_get_started_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0066FF)
                    ),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Get Started",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }

                // Page Indicator Dots (Dot 1 active)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.35f))
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.35f))
                    )
                }
            }
        }
    }
}

/**
 * Screen 2: Onboarding
 * - Clean white card background.
 * - Top right "Skip" button.
 * - Header: "Welcome to WorldMart" - "Your Global Marketplace".
 * - 4 Circular Category Icons: Buy Global, Sell Worldwide, Explore All Countries, Grow Your Business.
 * - Primary CTA: "Get Started ➔".
 * - Bottom link: "Already have an account? Login".
 */
@Composable
fun WorldMartOnboardingScreen(
    onSkip: () -> Unit,
    onGetStarted: () -> Unit,
    onGoToLogin: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar with Skip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Skip",
                    color = Color(0xFF0066FF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { onSkip() }
                        .padding(8.dp)
                        .testTag("onboarding_skip_btn")
                )
            }

            // Header Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Welcome to",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF0F172A)
                )
                Row {
                    Text(
                        text = "World",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Mart",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0066FF)
                    )
                }
                Text(
                    text = "Your Global Marketplace",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B)
                )
            }

            // 4 Circular Pillars (Buy Global, Sell Worldwide, Explore All Countries, Grow Your Business)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OnboardingBadgeItem(
                    icon = Icons.Default.ShoppingCart,
                    title = "Buy",
                    subtitle = "Global"
                )
                OnboardingBadgeItem(
                    icon = Icons.Default.LocalOffer,
                    title = "Sell",
                    subtitle = "Worldwide"
                )
                OnboardingBadgeItem(
                    icon = Icons.Default.Public,
                    title = "Explore",
                    subtitle = "All Countries"
                )
                OnboardingBadgeItem(
                    icon = Icons.Default.Shield,
                    title = "Grow",
                    subtitle = "Your Business"
                )
            }

            // Logistics Illustration Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF1F5F9),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.DirectionsBoat, contentDescription = null, tint = Color(0xFF0066FF), modifier = Modifier.size(36.dp))
                            Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(42.dp))
                            Icon(imageVector = Icons.Default.Inventory2, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(36.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Digitizing Odisha's Local Businesses for Global B2B & B2C Commerce",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Bottom CTAs
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(52.dp)
                        .testTag("onboarding_get_started_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Get Started", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Already have an account? ", fontSize = 13.sp, color = Color(0xFF64748B))
                    Text(
                        text = "Login",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0066FF),
                        modifier = Modifier.clickable { onGoToLogin() }
                    )
                }

                // Page Indicator Dots (Dot 2 active)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFCBD5E1)))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF0066FF)))
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFCBD5E1)))
                }
            }
        }
    }
}

/**
 * Screen 3: Sign Up / Login Screen
 * - Clean white card background.
 * - Title: "Create Account".
 * - Social login buttons: "Continue with Google", "Continue with Apple".
 * - Divider line "Or".
 * - Input fields: Full Name, Email Address, Mobile Number, Password.
 * - Primary blue button "Sign Up".
 * - Bottom link "Already have an account? Login".
 */
@Composable
fun WorldMartSignUpLoginScreen(
    onSubmit: (isRegister: Boolean, username: String, pass: String, fullName: String, email: String, mobile: String, role: String) -> Unit
) {
    var isSignUpMode by remember { mutableStateOf(true) }
    var fullName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("CUSTOMER") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = if (isSignUpMode) "Create Account" else "Welcome Back",
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )

            // Social Logins
            OutlinedButton(
                onClick = {
                    onSubmit(false, "admin", "admin123", "Super Admin", "admin@worldmart.com", "9876543210", "FOUNDER_CEO")
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color(0xFF4285F4), modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Continue with Google", color = Color(0xFF334155), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            OutlinedButton(
                onClick = {
                    onSubmit(false, "salesrep", "sales123", "Marcus Vance", "rep@worldmart.com", "9876543211", "SALES_REPRESENTATIVE")
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.PhoneIphone, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Continue with Apple", color = Color(0xFF334155), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            // Divider Or
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE2E8F0))
                Text(" Or ", color = Color(0xFF94A3B8), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE2E8F0))
            }

            if (isSignUpMode) {
                // Full Name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    placeholder = { Text("Enter full name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF94A3B8)) },
                    modifier = Modifier.fillMaxWidth().testTag("signup_fullname_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Mobile Number
                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    label = { Text("Mobile Number") },
                    placeholder = { Text("Enter mobile number") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF94A3B8)) },
                    modifier = Modifier.fillMaxWidth().testTag("signup_mobile_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Email Address
            OutlinedTextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = { Text("Email Address or Username") },
                placeholder = { Text("Enter email or username") },
                leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, tint = Color(0xFF94A3B8)) },
                modifier = Modifier.fillMaxWidth().testTag("signup_email_input"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("Enter password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF94A3B8)) },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle password visibility",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().testTag("signup_password_input"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Role selection for Sign Up
            if (isSignUpMode) {
                Text("Select Workspace Role:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF475569))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedRole == "CUSTOMER",
                        onClick = { selectedRole = "CUSTOMER" },
                        label = { Text("Customer") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = selectedRole == "SELLER",
                        onClick = { selectedRole = "SELLER" },
                        label = { Text("Seller") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = selectedRole == "SALES_REPRESENTATIVE",
                        onClick = { selectedRole = "SALES_REPRESENTATIVE" },
                        label = { Text("Sales Rep") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (errorMsg.isNotEmpty()) {
                Text(errorMsg, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }

            // Primary Action Button
            Button(
                onClick = {
                    if (emailAddress.isBlank() || password.isBlank()) {
                        errorMsg = "Please enter email/username and password."
                        return@Button
                    }
                    onSubmit(
                        isSignUpMode,
                        emailAddress.trim(),
                        password.trim(),
                        if (fullName.isBlank()) emailAddress else fullName.trim(),
                        emailAddress.trim(),
                        mobileNumber.trim(),
                        selectedRole
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("auth_submit_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text(
                    text = if (isSignUpMode) "Sign Up" else "Log In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Bottom Switcher
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = if (isSignUpMode) "Already have an account? " else "Don't have an account? ",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
                Text(
                    text = if (isSignUpMode) "Login" else "Sign Up",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0066FF),
                    modifier = Modifier.clickable {
                        isSignUpMode = !isSignUpMode
                        errorMsg = ""
                    }
                )
            }
        }
    }
}

@Composable
private fun FeatureChipIcon(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Text(text = label, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun OnboardingBadgeItem(icon: ImageVector, title: String, subtitle: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFEFF6FF))
                .border(1.5.dp, Color(0xFF0066FF).copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = Color(0xFF0066FF), modifier = Modifier.size(26.dp))
        }
        Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Text(text = subtitle, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B))
    }
}
