package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FinancialRecordEntity
import com.example.data.SellerEntity
import com.example.model.UserRole
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun AdminFinanceScreen(
    currentUserRole: UserRole,
    financialRecords: List<FinancialRecordEntity>,
    sellerList: List<SellerEntity>
) {
    if (!currentUserRole.canAccessFinance()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .testTag("admin_finance_restricted"),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFF87171)))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Permission Denied",
                        tint = Color(0xFFDC2626),
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Access Restricted: Finance Division Only",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF991B1B)
                    )
                    Text(
                        text = "Your current active role (${currentUserRole.title}) does not have clearance to view proprietary company ledgers, escrow payouts, or financial records.",
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color(0xFF7F1D1D)
                    )
                    Text(
                        text = "Authorized roles: Founder/CEO, Finance Director.",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF991B1B)
                    )
                }
            }
        }
        return
    }

    val totalGmv = sellerList.sumOf { it.monthlyGmv }
    val commissionEarned = totalGmv * 0.08
    val payoutsTotal = financialRecords.filter { it.type == "Payout" }.sumOf { it.amount }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_finance_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Financial Telemetry & Escrow Ledgers",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Treasury, seller payout reconciliation & revenue audit",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Summary Metric Cards
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FinanceStatCard(
                    title = "Monthly GMV",
                    amount = "$${String.format("%,.2f", totalGmv)}",
                    subtext = "District Order Volume",
                    color = StatusInfo,
                    modifier = Modifier.weight(1f)
                )
                FinanceStatCard(
                    title = "Platform Fee (8%)",
                    amount = "$${String.format("%,.2f", commissionEarned)}",
                    subtext = "Net Take Revenue",
                    color = EmeraldTeal,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            FinanceStatCard(
                title = "Total Escrow Disbursements",
                amount = "$${String.format("%,.2f", payoutsTotal)}",
                subtext = "Automated next-day merchant direct transfers",
                color = AmberGold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                text = "Recent Treasury & Settlement Transactions",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(financialRecords, key = { it.id }) { record ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = record.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(text = "${record.transactionRef} • ${record.district}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "Date: ${record.date}", fontSize = 10.sp, color = SlateTextMuted)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = (if (record.type == "Payout") "-$" else "+$") + String.format("%,.2f", record.amount),
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = if (record.type == "Payout") Color(0xFFDC2626) else EmeraldTeal
                        )
                        StatusBadge(status = record.category)
                    }
                }
            }
        }
    }
}

@Composable
fun FinanceStatCard(
    title: String,
    amount: String,
    subtext: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = amount, fontWeight = FontWeight.Black, fontSize = 18.sp, color = color)
            Text(text = subtext, fontSize = 10.sp, color = SlateTextMuted)
        }
    }
}
