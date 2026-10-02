package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (status.lowercase()) {
        "active", "verified", "live", "urgent", "completed" ->
            Pair(Color(0xFFD1FAE5), Color(0xFF065F46)) // emerald/teal
        "pending review", "in setup", "high", "pending documents", "under review" ->
            Pair(Color(0xFFFEF3C7), Color(0xFF92400E)) // amber/gold
        "suspended", "archived", "out of stock" ->
            Pair(Color(0xFFFEE2E2), Color(0xFF991B1B)) // red
        "paused", "draft", "low stock", "normal" ->
            Pair(Color(0xFFE2E8F0), Color(0xFF334155)) // slate
        else ->
            Pair(Color(0xFFDBEAFE), Color(0xFF1E40AF)) // blue
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = status.uppercase(),
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun DivisionBadge(
    division: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (division.lowercase()) {
        "leadership" -> Pair(Color(0xFFEDE9FE), Color(0xFF5B21B6)) // Purple
        "technology" -> Pair(Color(0xFFCCFBF1), Color(0xFF0F766E)) // Teal
        "marketing" -> Pair(Color(0xFFFCE7F3), Color(0xFF9D174D)) // Pink
        "finance" -> Pair(Color(0xFFFEF9C3), Color(0xFF854D0E)) // Amber
        "operations" -> Pair(Color(0xFFDBEAFE), Color(0xFF1E40AF)) // Blue
        "business development" -> Pair(Color(0xFFE0E7FF), Color(0xFF3730A3)) // Indigo
        "customer support" -> Pair(Color(0xFFD1FAE5), Color(0xFF065F46)) // Green
        else -> Pair(Color(0xFFF1F5F9), Color(0xFF475569))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = division,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
