package com.example.ui.publicsite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.PublicPage

@Composable
fun PublicBecomeSellerScreen(
    onNavigateToRegistration: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_become_seller_screen"),
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
                    text = "EXPAND YOUR REACH",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Become a Verified World Mart Seller",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap into thousands of active district shoppers with zero upfront software costs and automated last-mile dispatch.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onNavigateToRegistration,
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("become_seller_start_reg_button")
                ) {
                    Icon(imageVector = Icons.Default.AppRegistration, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start Seller Registration", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        // Why Sell with Us
        Text(
            text = "Why Top Local Retailers Choose World Mart",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SellerPerkCard(
                icon = Icons.Default.TrendingUp,
                title = "+42% Avg. Sales",
                desc = "Merchants experience significant revenue lift within the first 60 days.",
                modifier = Modifier.weight(1f)
            )
            SellerPerkCard(
                icon = Icons.Default.Speed,
                title = "2-Hour Pickup",
                desc = "Our drivers collect packed items from your shop so you never handle shipping.",
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SellerPerkCard(
                icon = Icons.Default.SupportAgent,
                title = "Dedicated Rep",
                desc = "A physical field representative visits your shop to help with photos & setup.",
                modifier = Modifier.weight(1f)
            )
            SellerPerkCard(
                icon = Icons.Default.Payments,
                title = "Next-Day Payouts",
                desc = "Guaranteed direct bank deposits with no frozen reserves or hidden deductions.",
                modifier = Modifier.weight(1f)
            )
        }

        // Transparent Pricing Tiers
        Text(
            text = "Transparent Commission Structure",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Standard Merchant", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(text = "Ideal for independent boutiques, bakeries & shops", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(text = "8%", fontWeight = FontWeight.Black, fontSize = 24.sp, color = EmeraldTeal)
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    FeeCheck("Free Turn-key branded E-Shop storefront")
                    FeeCheck("Full access to 2-hour district delivery fleet")
                    FeeCheck("Real-time Merchant Analytics Dashboard")
                    FeeCheck("Zero monthly maintenance or listing fees")
                }
            }
        }

        // Onboarding Requirements
        Card(
            colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Simple Seller Prerequisites", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "1. Valid regional business license or municipal trade registration.", color = Color(0xFFCCFBF1), fontSize = 12.sp)
                Text(text = "2. Physical storefront or certified regional warehouse in an active district.", color = Color(0xFFCCFBF1), fontSize = 12.sp)
                Text(text = "3. Active commercial bank account for automated next-day payout transfers.", color = Color(0xFFCCFBF1), fontSize = 12.sp)
            }
        }

        Button(
            onClick = onNavigateToRegistration,
            colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("apply_as_seller_bottom_button")
        ) {
            Text("Proceed to Seller Registration Form", color = CorporateNavyDark, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Composable
fun SellerPerkCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, desc: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(22.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
        }
    }
}

@Composable
fun FeeCheck(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(16.dp))
        Text(text = text, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
