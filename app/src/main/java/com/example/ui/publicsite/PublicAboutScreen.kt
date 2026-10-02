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

@Composable
fun PublicAboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_about_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Banner
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
                    text = "ABOUT WORLD MART",
                    color = EmeraldTealLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Empowering Neighborhood Commerce with Digital Scale",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Company Story
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.HistoryEdu, contentDescription = null, tint = EmeraldTeal)
                    Text(text = "Our Story", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
                Text(
                    text = "Founded in 2024 by retail veteran Julian Vance and distributed systems engineer Elena Rostova, World Mart was born out of a critical observation: local merchants are the lifeblood of regional culture, yet traditional e-commerce models squeeze them into unsustainable margins.",
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "World Mart set out to build an egalitarian platform — one that equips local independent retailers with the exact high-speed digital infrastructure, unified delivery networks, and payment routing that giant e-tailers possess, while celebrating regional brand identity.",
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Core Values
        Text(
            text = "Our Core Values",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ValueCard(
                icon = Icons.Default.Storefront,
                title = "Local-First",
                desc = "We champion independent storefronts and neighborhood wealth creation.",
                modifier = Modifier.weight(1f)
            )
            ValueCard(
                icon = Icons.Default.LockOpen,
                title = "Transparency",
                desc = "Fair, clear fee structures with no hidden algorithm penalties.",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ValueCard(
                icon = Icons.Default.Psychology,
                title = "Tech Equality",
                desc = "Zero-barrier mobile POS and automated store digitizing.",
                modifier = Modifier.weight(1f)
            )
            ValueCard(
                icon = Icons.Default.Eco,
                title = "Sustainability",
                desc = "District-consolidated delivery minimizing vehicular emissions.",
                modifier = Modifier.weight(1f)
            )
        }

        // Platform Key Figures
        Card(
            colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "World Mart by the Numbers",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricMini(title = "500+", label = "Verified Sellers")
                    MetricMini(title = "5", label = "Districts Covered")
                    MetricMini(title = "2-Hour", label = "Avg. Delivery")
                    MetricMini(title = "99.4%", label = "Merchant CSAT")
                }
            }
        }
    }
}

@Composable
fun ValueCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(24.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
        }
    }
}

@Composable
fun MetricMini(title: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, color = EmeraldTealLight, fontWeight = FontWeight.Black, fontSize = 16.sp)
        Text(text = label, color = Color(0xFF94A3B8), fontSize = 10.sp)
    }
}
