package com.example.ui.publicsite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.AnnouncementEntity
import com.example.ui.theme.*

@Composable
fun PublicNewsScreen(
    newsList: List<AnnouncementEntity>
) {
    var newsletterEmail by remember { mutableStateOf("") }
    var subscribedMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("public_news_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Banner
        item {
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
                        text = "COMMUNICATIONS & DISPATCHES",
                        color = EmeraldTealLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "News & Updates",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Press releases, product releases, and district expansion announcements.",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Newsletter Subscription Box
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.MarkEmailRead, contentDescription = null, tint = EmeraldTeal)
                        Text(text = "Subscribe to World Mart Dispatches", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Text(
                        text = "Receive quarterly corporate earnings summaries, regional seller grants notices, and district platform rollouts.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newsletterEmail,
                            onValueChange = { newsletterEmail = it },
                            placeholder = { Text("corporate.email@domain.com", fontSize = 12.sp) },
                            singleLine = true,
                            modifier = Modifier.weight(1f).testTag("newsletter_email_input"),
                            shape = RoundedCornerShape(8.dp)
                        )
                        Button(
                            onClick = {
                                if (newsletterEmail.contains("@")) {
                                    subscribedMessage = "Subscribed $newsletterEmail successfully."
                                    newsletterEmail = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("newsletter_subscribe_button")
                        ) {
                            Text("Subscribe")
                        }
                    }
                    if (subscribedMessage != null) {
                        Text(text = subscribedMessage ?: "", color = EmeraldTeal, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // News List
        items(newsList, key = { it.id }) { news ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.testTag("news_card_${news.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFCCFBF1)
                        ) {
                            Text(
                                text = news.category.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldTealMuted,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Text(text = news.date, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Text(
                        text = news.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 21.sp
                    )

                    Text(
                        text = news.summary,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 19.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = news.content,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Text(
                        text = "Published by: ${news.authorRole}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
