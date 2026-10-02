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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun PublicServicesScreen() {
    val services = listOf(
        ServiceItemData(
            title = "Multi-Vendor Marketplace Infrastructure",
            icon = Icons.Default.Language,
            desc = "Unified high-capacity cloud shopping portal allowing customers to browse hundreds of neighborhood shops in one synchronized digital catalog with sub-50ms search.",
            features = listOf("Cross-vendor unified checkout", "District-filtered store locator", "Customer mobile & web storefronts")
        ),
        ServiceItemData(
            title = "Turn-Key E-Shop Generator",
            icon = Icons.Default.Storefront,
            desc = "Independent retailers receive an instant branded digital storefront with custom URL, customized categories, automated stock sync, and digital promotional banners.",
            features = listOf("Zero coding required", "Self-service product management", "Store operating hours & pickup slots")
        ),
        ServiceItemData(
            title = "District Consolidated Logistics Fleet",
            icon = Icons.Default.LocalShipping,
            desc = "World Mart's dedicated local courier network picks up packaged items directly from merchant storefronts and consolidates multi-merchant customer orders for 2-hour delivery.",
            features = listOf("Sub-2-hour delivery radius", "Consolidated single-driver drop-off", "Cold-chain produce containers")
        ),
        ServiceItemData(
            title = "WorldMart Pay & Instant Settlement",
            icon = Icons.Default.AccountBalance,
            desc = "PCI-DSS Level 1 compliant escrow payment architecture supporting all major digital wallets, debit/credit cards, and next-day direct merchant bank payouts.",
            features = listOf("Automated escrow release", "Transparent transaction fees", "No rolling reserve lockup")
        ),
        ServiceItemData(
            title = "Merchant Business Analytics Studio",
            icon = Icons.Default.Analytics,
            desc = "Actionable commercial intelligence giving local owners insights into trending regional search terms, peak ordering hours, repeat buyer demographics, and inventory demand forecasts.",
            features = listOf("Real-time revenue reports", "District category benchmarks", "Top selling SKU heatmaps")
        ),
        ServiceItemData(
            title = "Local Merchant Growth Grants & POS Hardware",
            icon = Icons.Default.MonetizationOn,
            desc = "Empowering traditional brick-and-mortar storefronts with sponsored barcode scanners, touch POS tablets, and professional catalog photography services.",
            features = listOf("Hardware financing options", "On-site staff POS training", "Professional product photography")
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_services_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
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
                    text = "CORE CAPABILITIES",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "World Mart Commercial Services",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Comprehensive digital and logistical infrastructure built for local commerce.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        // Service Items
        services.forEach { service ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFCCFBF1)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = service.icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(22.dp))
                        }
                        Text(
                            text = service.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Text(
                        text = service.desc,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        service.features.forEach { feat ->
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(14.dp))
                                Text(text = feat, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class ServiceItemData(
    val title: String,
    val icon: ImageVector,
    val desc: String,
    val features: List<String>
)
