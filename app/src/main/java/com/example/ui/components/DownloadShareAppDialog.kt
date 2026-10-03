package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/**
 * Prominent Download & Share Dialog allowing users and shopkeepers to:
 * 1. Share the World Mart app via WhatsApp / Telegram with 1-click.
 * 2. View step-by-step instructions to install World Mart directly to their Android phone home screen.
 * 3. Copy the official download link directly to clipboard.
 */
@Composable
fun DownloadShareAppDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }

    val appShareUrl = "https://ais-pre-ih3ulsmbm3n643tieirspf-353460025929.asia-east1.run.app"
    val shareText = "Download & Install World Mart App on your phone!\nOdisha's Official Multi-Vendor Commerce Platform:\n$appShareUrl"

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("download_share_app_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Badge
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF0066FF), Color(0xFF00E5FF))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.GetApp,
                        contentDescription = "Download Icon",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Text(
                    text = "Download World Mart App",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Install directly on your phone or share with shopkeepers & friends for 100% FREE!",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(color = Color(0xFFE2E8F0))

                // Primary Direct APK / Web App Download Launcher Button
                Button(
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(appShareUrl))
                        try {
                            context.startActivity(browserIntent)
                        } catch (e: Exception) {
                            // Fallback
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("direct_download_apk_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download APK",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "📥 Direct Download & Open App",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Action 1: Share via WhatsApp
                Button(
                    onClick = {
                        val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://api.whatsapp.com/send?text=${Uri.encode(shareText)}")
                        }
                        try {
                            context.startActivity(whatsappIntent)
                        } catch (e: Exception) {
                            val genericIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(genericIntent, "Share World Mart App"))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("share_whatsapp_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "WhatsApp",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Share via WhatsApp / Telegram",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Action 2: Step-by-Step Installation Instructions
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PhoneIphone,
                                contentDescription = null,
                                tint = Color(0xFF0066FF),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "How to Install on Phone Screen:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }

                        Text("1. Open the shared link in Google Chrome.", fontSize = 12.sp, color = Color(0xFF334155))
                        Text("2. Tap the 3 dots (⋮) menu at the top right.", fontSize = 12.sp, color = Color(0xFF334155))
                        Text("3. Tap \"Add to Home screen\" or \"Install App\".", fontSize = 12.sp, color = Color(0xFF334155))
                        Text("4. World Mart app icon will appear on your phone screen!", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0066FF))
                    }
                }

                // Action 3: Copy Direct App Link
                OutlinedButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                        val clip = android.content.ClipData.newPlainText("World Mart Link", appShareUrl)
                        clipboard.setPrimaryClip(clip)
                        isCopied = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("copy_app_link_btn"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = if (isCopied) Color(0xFF10B981) else Color(0xFF0066FF)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isCopied) "App Link Copied!" else "Copy Direct Download Link",
                        color = if (isCopied) Color(0xFF10B981) else Color(0xFF0066FF),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Close Button
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close", color = Color(0xFF64748B), fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
