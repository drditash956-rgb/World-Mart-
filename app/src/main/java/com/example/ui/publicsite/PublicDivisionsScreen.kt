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
import com.example.ui.components.DivisionBadge
import com.example.ui.theme.*

data class DivisionDetail(
    val name: String,
    val headName: String,
    val icon: ImageVector,
    val tagline: String,
    val objectives: List<String>,
    val teamSize: String
)

@Composable
fun PublicDivisionsScreen() {
    val divisionsList = listOf(
        DivisionDetail(
            name = "Leadership",
            headName = "Julian Vance, CEO & Co-Founder",
            icon = Icons.Default.Shield,
            tagline = "Corporate stewardship, capital allocation, and municipal expansion governance.",
            objectives = listOf(
                "Establishing macroeconomic strategy & multi-city trade corridors.",
                "Overseeing board relations, legal compliance, and investor capital.",
                "Championing hyper-local economic independence."
            ),
            teamSize = "4 Officers"
        ),
        DivisionDetail(
            name = "Technology",
            headName = "Elena Rostova, CTO & Co-Founder",
            icon = Icons.Default.Code,
            tagline = "Scalable microservices, automated routing, and merchant cloud tooling.",
            objectives = listOf(
                "Engineering zero-latency multi-vendor shopping cart engines.",
                "Building Android and Web POS systems for neighborhood shop owners.",
                "Enforcing high-availability infrastructure and data security."
            ),
            teamSize = "18 Engineers"
        ),
        DivisionDetail(
            name = "Operations",
            headName = "Camila Duarte, VP of Operations",
            icon = Icons.Default.LocalShipping,
            tagline = "District fulfillment hubs, courier fleets, and on-site merchant onboarding.",
            objectives = listOf(
                "Managing 2-hour consolidated parcel deliveries across 5 districts.",
                "Directing field dispatch captains and physical store inspections.",
                "Operating localized packaging consolidation centers."
            ),
            teamSize = "42 Logistics Specialists"
        ),
        DivisionDetail(
            name = "Marketing",
            headName = "Siddharth Rao, Director of Marketing",
            icon = Icons.Default.Campaign,
            tagline = "Brand visibility, consumer adoption funnels, and public merchant stories.",
            objectives = listOf(
                "Driving local consumer awareness for neighborhood shops.",
                "Publishing authentic merchant spotlight documentaries.",
                "Managing corporate media, press releases, and district PR."
            ),
            teamSize = "9 Creatives & Growth Leads"
        ),
        DivisionDetail(
            name = "Finance",
            headName = "Dr. Tariq Al-Mansoor, CFO",
            icon = Icons.Default.Payments,
            tagline = "Automated merchant payouts, escrow reconciliation, and platform economics.",
            objectives = listOf(
                "Facilitating next-day escrow payouts directly to store accounts.",
                "Managing merchant growth capital and equipment financing.",
                "Enforcing financial reporting and tax compliance standards."
            ),
            teamSize = "6 Financial Analysts"
        ),
        DivisionDetail(
            name = "Business Development",
            headName = "Amina Kente, Head of BizDev",
            icon = Icons.Default.TrendingUp,
            tagline = "Merchant associations, enterprise retail partners, and regional co-ops.",
            objectives = listOf(
                "Negotiating strategic partnerships with local trade chambers.",
                "Onboarding flagship anchor sellers in each district.",
                "Expanding into new merchandise and artisanal categories."
            ),
            teamSize = "8 Partnership Managers"
        ),
        DivisionDetail(
            name = "Customer Support",
            headName = "Rachel Chen, Head of Support",
            icon = Icons.Default.SupportAgent,
            tagline = "24/7 bilingual dispute mediation, shopper care, and merchant ticketing.",
            objectives = listOf(
                "Maintaining < 3 minute response time for active order questions.",
                "Handling seller technical onboarding and catalog assistance.",
                "Resolving return and replacement requests seamlessly."
            ),
            teamSize = "14 Support Specialists"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("public_divisions_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner Header
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
                    text = "ORGANIZATIONAL STRUCTURE",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "World Mart Central Divisions",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Seven specialized divisions operating collaboratively to power regional commerce.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        // Division Cards
        divisionsList.forEach { div ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFCCFBF1)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = div.icon, contentDescription = null, tint = EmeraldTeal, modifier = Modifier.size(20.dp))
                            }
                            Text(text = div.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                        DivisionBadge(division = div.name)
                    }

                    Text(text = div.tagline, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Text(
                        text = "Head: ${div.headName}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Key Objectives:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        div.objectives.forEach { obj ->
                            Text(text = "• $obj", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "Headcount: ${div.teamSize}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
