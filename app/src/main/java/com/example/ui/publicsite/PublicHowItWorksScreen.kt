package com.example.ui.publicsite

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun PublicHowItWorksScreen() {
    var selectedTab by remember { mutableStateOf(0) } // 0: For Sellers, 1: For Shoppers

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_how_it_works_screen"),
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
                    text = "OPERATIONAL WORKFLOW",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "How World Mart Operates",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dual-path interactive guide for local businesses and community consumers.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        // Tab Selector: For Sellers vs For Shoppers
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = EmeraldTeal
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("For Local Sellers", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                icon = { Icon(Icons.Default.Storefront, contentDescription = null, modifier = Modifier.size(18.dp)) },
                modifier = Modifier.testTag("how_it_works_tab_sellers")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("For District Shoppers", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                icon = { Icon(Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(18.dp)) },
                modifier = Modifier.testTag("how_it_works_tab_shoppers")
            )
        }

        if (selectedTab == 0) {
            // Seller Flow
            FlowStepCard(
                step = "Step 1",
                title = "Register Store & Submit Trade Details",
                desc = "Enter your business category, address, and contact information through the online registration portal in under 3 minutes.",
                icon = Icons.Default.AppRegistration
            )
            FlowStepCard(
                step = "Step 2",
                title = "Field Representative On-Site Verification",
                desc = "Our designated district sales representative visits your storefront to verify inventory authenticity, configure POS hardware, and snap professional catalog images.",
                icon = Icons.Default.VerifiedUser
            )
            FlowStepCard(
                step = "Step 3",
                title = "Instant Branded E-Shop Launch",
                desc = "Your custom digital storefront goes live on the World Mart marketplace with your catalog, opening hours, and promotional discount badges.",
                icon = Icons.Default.Web
            )
            FlowStepCard(
                step = "Step 4",
                title = "Receive Orders & 2-Hour Driver Pickup",
                desc = "When local shoppers buy, you receive an instant audio and notification alert. Simply bag the items; World Mart's courier handles district delivery.",
                icon = Icons.Default.LocalShipping
            )
            FlowStepCard(
                step = "Step 5",
                title = "Automated Next-Day Bank Payout",
                desc = "Escrow settlements clear directly into your business bank account every morning, accompanied by itemized tax invoices.",
                icon = Icons.Default.AccountBalance
            )
        } else {
            // Shopper Flow
            FlowStepCard(
                step = "Step 1",
                title = "Explore Neighborhood Merchants",
                desc = "Browse verified local bakeries, tech stores, fashion boutiques, and specialty grocers within your specific district.",
                icon = Icons.Default.LocationOn
            )
            FlowStepCard(
                step = "Step 2",
                title = "Unified Multi-Store Basket",
                desc = "Add sourdough bread from the baker and headphones from the electronics dealer into a single combined cart with one checkout total.",
                icon = Icons.Default.AddShoppingCart
            )
            FlowStepCard(
                step = "Step 3",
                title = "Instant Secure Payment",
                desc = "Pay securely via credit card, mobile wallet, or bank transfer with zero markups over in-store shelf prices.",
                icon = Icons.Default.Payment
            )
            FlowStepCard(
                step = "Step 4",
                title = "Consolidated 2-Hour District Delivery",
                desc = "Our consolidated fleet picks up all your items across your neighborhood and delivers them right to your doorstep in one consolidated parcel.",
                icon = Icons.Default.ElectricScooter
            )
            FlowStepCard(
                step = "Step 5",
                title = "Strengthen Community Wealth",
                desc = "Your purchases directly sustain regional small businesses, independent craftspeople, and municipal jobs.",
                icon = Icons.Default.Favorite
            )
        }
    }
}

@Composable
fun FlowStepCard(step: String, title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFCCFBF1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(24.dp))
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = step.uppercase(), color = AmberGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 18.sp)
            }
        }
    }
}
