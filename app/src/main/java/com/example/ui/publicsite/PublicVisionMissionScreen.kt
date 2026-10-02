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
fun PublicVisionMissionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_vision_mission_screen"),
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
                    text = "PURPOSE & DIRECTION",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Vision & Mission of World Mart",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Mission Statement Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.Flag, contentDescription = null, tint = EmeraldTeal)
                    Text(text = "Our Corporate Mission", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
                Text(
                    text = "To decentralize commerce by giving independent regional sellers the same technology, logistics, and capital power as multinational retail giants while preserving community character.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Every transaction facilitated on World Mart keeps capital circulating directly within local municipal economies, providing sustainable livelihoods for neighborhood artisans, retailers, and families.",
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 2030 Vision Card
        Card(
            colors = CardDefaults.cardColors(containerColor = CorporateNavyDark),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = AmberGold)
                    Text(text = "Our Long-Term 2030 Vision", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Text(
                    text = "A seamless national network of 50+ connected metropolitan districts where any customer can receive authentic local goods within hours, and any merchant can sell digitally with zero technical friction.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 22.sp,
                    color = Color(0xFFCCFBF1)
                )
            }
        }

        // Strategic Pillars
        Text(
            text = "Four Pillars of Long-Term Strategy",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        PillarItem(
            num = "01",
            title = "Hyper-Local Velocity",
            desc = "Sub-2-hour local parcel deliveries that beat traditional central warehouse delivery speeds by days."
        )
        PillarItem(
            num = "02",
            title = "Merchant Autonomy",
            desc = "Store owners keep control of customer relationships, personalized store branding, and fair pricing."
        )
        PillarItem(
            num = "03",
            title = "Universal Access",
            desc = "Removing high upfront technology costs by offering zero-capital POS terminals and automated inventory sync."
        )
        PillarItem(
            num = "04",
            title = "Eco-Centric Logistics",
            desc = "Electric micro-fleet couriers and recyclable district packaging hubs to eliminate cardboard waste."
        )
    }
}

@Composable
fun PillarItem(num: String, title: String, desc: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = num, color = AmberGold, fontWeight = FontWeight.Black, fontSize = 20.sp)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 17.sp)
            }
        }
    }
}
