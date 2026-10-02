package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CorporateNavyDark
import com.example.ui.theme.EmeraldTeal

/**
 * High-fidelity vector recreation of the Official World Mart Logo from Image 2.
 * Features:
 * - Glowing circular cyan/electric blue ring.
 * - Dark midnight navy background.
 * - Shopping cart with blue Earth globe and golden orbital ring.
 * - Silver "WORLD" and Golden "MART" typography.
 * - Tagline: "EVERY PRODUCT • EVERY COUNTRY • EVERY BUSINESS • EVERYONE".
 * - 4 Badges: BUY, SELL, CONNECT, GROW.
 */
@Composable
fun WorldMartLogoBadge(
    modifier: Modifier = Modifier,
    size: Dp = 280.dp,
    showTaglineAndBadges: Boolean = true
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(16.dp, CircleShape, spotColor = Color(0xFF00E5FF))
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF0A192F),
                        Color(0xFF020B1C),
                        Color(0xFF00050D)
                    )
                )
            )
            .border(
                width = 3.5.dp,
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF00E5FF),
                        Color(0xFF0066FF),
                        Color(0xFF38BDF8),
                        Color(0xFF00E5FF)
                    )
                ),
                shape = CircleShape
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Cart + Globe Icon Top
            Box(
                modifier = Modifier
                    .size(if (showTaglineAndBadges) 76.dp else 100.dp)
                    .padding(bottom = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = this.size.width
                    val height = this.size.height

                    // Background Glow behind Globe
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF00E5FF).copy(alpha = 0.35f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.4f),
                            radius = width * 0.45f
                        )
                    )
                }

                // Globe Icon
                Box(
                    modifier = Modifier
                        .size(if (showTaglineAndBadges) 48.dp else 64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF0066FF), Color(0xFF00E5FF))
                            )
                        )
                        .border(1.5.dp, Color(0xFFF59E0B), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Public,
                        contentDescription = "World Globe",
                        tint = Color.White,
                        modifier = Modifier.size(if (showTaglineAndBadges) 32.dp else 44.dp)
                    )
                }

                // Shopping Cart overlay
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping Cart",
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier
                        .size(if (showTaglineAndBadges) 62.dp else 80.dp)
                        .offset(y = 6.dp)
                )
            }

            // Typography: WORLD MART
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "WORLD ",
                    fontSize = if (showTaglineAndBadges) 20.sp else 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "MART",
                    fontSize = if (showTaglineAndBadges) 20.sp else 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFF59E0B),
                    letterSpacing = 1.sp
                )
            }

            if (showTaglineAndBadges) {
                Spacer(modifier = Modifier.height(4.dp))

                // Subtitle / Tagline
                Text(
                    text = "EVERY PRODUCT • EVERY COUNTRY",
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFCBD5E1),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "EVERY BUSINESS • EVERYONE",
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38BDF8),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 4 Action Badges: BUY, SELL, CONNECT, GROW
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LogoMiniBadge(icon = Icons.Default.ShoppingBag, label = "BUY", color = Color(0xFF00E5FF))
                    LogoMiniBadge(icon = Icons.Default.Storefront, label = "SELL", color = Color(0xFFF59E0B))
                    LogoMiniBadge(icon = Icons.Default.Handshake, label = "CONNECT", color = Color(0xFF10B981))
                    LogoMiniBadge(icon = Icons.Default.Language, label = "GROW", color = Color(0xFFA855F7))
                }
            }
        }
    }
}

@Composable
private fun LogoMiniBadge(
    icon: ImageVector,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f))
                .border(1.dp, color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(11.dp)
            )
        }
        Text(
            text = label,
            fontSize = 6.5.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color
        )
    }
}
