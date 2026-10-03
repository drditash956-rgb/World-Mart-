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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.PublicPage

@Composable
fun PublicHomeScreen(
    heroHeadline: String,
    heroSubtext: String,
    onNavigate: (PublicPage) -> Unit,
    onOpenDownloadShareDialog: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .testTag("public_home_screen")
    ) {
        // --- 1. HERO SECTION ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(CorporateNavyDark, CorporateNavySurface, CorporateNavyLight)
                    )
                )
                .padding(horizontal = 20.dp, vertical = 32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Tagline Badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = EmeraldTeal.copy(alpha = 0.25f),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(EmeraldTealLight, AmberGold)))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(EmeraldTealLight)
                        )
                        Text(
                            text = "NEXT-GEN LOCAL COMMERCE PLATFORM",
                            color = EmeraldTealLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Brand Name
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "WORLD",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MART",
                        color = EmeraldTealLight,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Main Mandated Headline
                Text(
                    text = heroHeadline.ifBlank { "Building a Connected Marketplace for Businesses and Customers." },
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Subtext
                Text(
                    text = heroSubtext.ifBlank { "Uniting neighborhood retailers, specialty stores, and local shoppers into one unified high-speed digital ecosystem." },
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 3 Hero Buttons (Mandated)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onNavigate(PublicPage.BECOME_A_SELLER) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(48.dp)
                            .testTag("hero_become_seller_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Storefront, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Become a Seller", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onNavigate(PublicPage.ABOUT) },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_explore_button"),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(Color(0xFF64748B), Color(0xFF94A3B8)))),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Explore World Mart", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        OutlinedButton(
                            onClick = { onNavigate(PublicPage.CAREERS) },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_join_team_button"),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGoldLight),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(AmberGold, AmberGoldLight))),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.GroupAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Join Our Team", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    // Download & Share App Banner Button
                    Button(
                        onClick = onOpenDownloadShareDialog,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(50.dp)
                            .testTag("hero_download_share_app_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(imageVector = Icons.Default.GetApp, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("📲 Download / Share App with Friends", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }

        // --- 2. WHAT WORLD MART IS ---
        SectionContainer(
            tag = "SECTION: OVERVIEW",
            title = "What World Mart Is",
            subtitle = "The modern infrastructure uniting neighborhood businesses and customers"
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "World Mart is a multi-vendor, multi-category digital marketplace platform designed specifically to bridge physical neighborhood stores with digital-first consumers.",
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Unlike traditional monolithic e-commerce platforms that marginalize brick-and-mortar merchants, World Mart provides local store owners with instant turn-key e-shops, district-wide consolidated deliveries, and unified checkout.",
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // --- 3. MARKETPLACE ECOSYSTEM ---
        SectionContainer(
            tag = "INTEGRATED PLATFORM",
            title = "Our Marketplace Ecosystem",
            subtitle = "Four synchronized pillars powering decentralized district trade"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                EcosystemCard(
                    icon = Icons.Default.Storefront,
                    title = "1. Vetted Local Sellers",
                    desc = "Verified neighborhood bakeries, electronics dealers, fashion boutiques, and specialty grocers."
                )
                EcosystemCard(
                    icon = Icons.Default.Dns,
                    title = "2. World Mart Cloud Engine",
                    desc = "Real-time inventory syncing, automated multi-vendor order routing, and merchant POS software."
                )
                EcosystemCard(
                    icon = Icons.Default.LocalShipping,
                    title = "3. District Last-Mile Fleet",
                    desc = "Consolidated cross-vendor delivery courier fleet fulfilling orders within 2 hours."
                )
                EcosystemCard(
                    icon = Icons.Default.ShoppingBag,
                    title = "4. Single-Basket Consumers",
                    desc = "Shoppers can purchase items from 5 distinct neighborhood stores in a single checkout."
                )
            }
        }

        // --- 4. BENEFITS FOR BUSINESSES ---
        SectionContainer(
            tag = "FOR MERCHANTS",
            title = "Benefits for Businesses",
            subtitle = "Empowering physical stores with enterprise retail technology"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BenefitItem(icon = Icons.Default.RocketLaunch, text = "Instant E-Shop Deployment: Go live with a branded digital storefront in under 24 hours.")
                BenefitItem(icon = Icons.Default.AllInclusive, text = "Zero Logistics Overhead: World Mart manages pickup, packaging materials, and last-mile dispatch.")
                BenefitItem(icon = Icons.Default.Payments, text = "Guaranteed Next-Day Payouts: Automated escrow settlement straight to merchant bank accounts.")
                BenefitItem(icon = Icons.Default.Analytics, text = "Predictive Demand Analytics: Gain district-wide purchasing insight to optimize stock.")
            }
        }

        // --- 5. BENEFITS FOR CUSTOMERS ---
        SectionContainer(
            tag = "FOR SHOPPERS",
            title = "Benefits for Customers",
            subtitle = "Fast, authentic, and connected local shopping"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BenefitItem(icon = Icons.Default.Bolt, text = "2-Hour Hyper-Local Delivery: Genuine goods delivered from your own town within hours.")
                BenefitItem(icon = Icons.Default.ShoppingBasket, text = "Multi-Store Cart: Combine fashion, fresh produce, and hardware in one order.")
                BenefitItem(icon = Icons.Default.Verified, text = "100% Authenticity Guaranteed: Every merchant is verified with physical inspections.")
                BenefitItem(icon = Icons.Default.SupportAgent, text = "Local Customer Care: 24/7 localized support and hassle-free district returns.")
            }
        }

        // --- 6. TECHNOLOGY ---
        SectionContainer(
            tag = "TECH STACK",
            title = "Enterprise Architecture",
            subtitle = "Built on high-reliability distributed systems"
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = EmeraldTealLight)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Zero-Trust Merchant Cloud & Edge APIs", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Text(
                        text = "World Mart runs on distributed microservices capable of sub-50ms catalog indexing and dynamic routing across thousands of simultaneous orders.",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TechBadge("Real-Time POS")
                        TechBadge("Automated Dispatch")
                        TechBadge("Escrow Gateway")
                    }
                }
            }
        }

        // --- 7. FUTURE EXPANSION ---
        SectionContainer(
            tag = "ROADMAP 2026-2028",
            title = "Future Expansion",
            subtitle = "Scaling community commerce to 50+ metropolitan regions"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ExpansionMilestone(
                    phase = "Phase 1 (Current)",
                    title = "5 Core Metropolitan Districts",
                    desc = "Full deployment in Central Metro, West Harbor, East Valley, North Park, and South Hub."
                )
                ExpansionMilestone(
                    phase = "Phase 2 (Q1 2027)",
                    title = "Regional Cross-District Express",
                    desc = "Next-day inter-city merchant exchanges connecting 18 secondary regional hubs."
                )
                ExpansionMilestone(
                    phase = "Phase 3 (2028)",
                    title = "Smart Automated Locker Network",
                    desc = "Temperature-controlled 24/7 pickup lockers for automated grocery and pharmacy orders."
                )
            }
        }

        // --- 8. SELLER ONBOARDING TIMELINE ---
        SectionContainer(
            tag = "HOW TO JOIN",
            title = "Simple Seller Onboarding",
            subtitle = "From registration to your first online sale in 4 seamless steps"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                TimelineStep(step = "1", title = "Submit Online Registration", desc = "Fill out business category, district, and contact details in 3 minutes.")
                TimelineStep(step = "2", title = "Physical Field Verification", desc = "Our assigned field representative visits your storefront to verify credentials.")
                TimelineStep(step = "3", title = "E-Shop & Catalog Setup", desc = "We provide free assistance in uploading products, descriptions, and pricing.")
                TimelineStep(step = "4", title = "Start Receiving District Orders", desc = "Your store goes live to thousands of local customers with instant order alerts.")
            }
        }

        // --- 9. CALL TO ACTION ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(
                    Brush.linearGradient(
                        listOf(EmeraldTealMuted, CorporateNavyDark)
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Ready to Grow With World Mart?",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Join hundreds of thriving local sellers expanding their reach through our marketplace technology.",
                    color = Color(0xFFCCFBF1),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
                Button(
                    onClick = { onNavigate(PublicPage.SELLER_REGISTRATION) },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(48.dp)
                        .testTag("cta_register_seller_button")
                ) {
                    Text("Register Your Store Today", color = CorporateNavyDark, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SectionContainer(
    tag: String,
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = tag,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldTeal,
            letterSpacing = 1.sp
        )
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        content()
    }
}

@Composable
fun EcosystemCard(icon: ImageVector, title: String, desc: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFCCFBF1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(22.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
            }
        }
    }
}

@Composable
fun BenefitItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(18.dp))
        Text(text = text, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp)
    }
}

@Composable
fun TechBadge(text: String) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = CorporateNavyLight
    ) {
        Text(
            text = text,
            color = EmeraldTealLight,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ExpansionMilestone(phase: String, title: String, desc: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = phase, color = AmberGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
fun TimelineStep(step: String, title: String, desc: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(EmeraldTeal),
            contentAlignment = Alignment.Center
        ) {
            Text(text = step, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
        }
    }
}
