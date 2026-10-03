package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.model.UserRole
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldMartAppBar(
    isAdminMode: Boolean,
    currentUserRole: UserRole,
    onToggleMode: (Boolean) -> Unit,
    onRoleChange: (UserRole) -> Unit,
    onOpenNavDrawer: () -> Unit,
    onOpenDownloadShareDialog: () -> Unit = {}
) {
    var showRoleMenu by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("world_mart_app_bar"),
        color = CorporateNavyDark,
        tonalElevation = 6.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Brand Logo & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.clickable { onOpenNavDrawer() }
                ) {
                    IconButton(
                        onClick = onOpenNavDrawer,
                        modifier = Modifier.testTag("app_nav_drawer_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open Navigation Menu",
                            tint = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(EmeraldTeal, AmberGold)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = "World Mart Logo",
                            tint = CorporateNavyDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "WORLD",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "MART",
                                color = EmeraldTealLight,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }
                        Text(
                            text = if (isAdminMode) "Operations & CMS Portal" else "Connected Marketplace",
                            color = Color(0xFF94A3B8),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Right Mode Switcher & Role Selector
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Download & Share App Button Icon
                    IconButton(
                        onClick = onOpenDownloadShareDialog,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0066FF))
                            .testTag("appbar_download_share_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.GetApp,
                            contentDescription = "Download & Share App",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Portal Mode Switcher Pill
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isAdminMode) AmberGold.copy(alpha = 0.2f) else EmeraldTeal.copy(alpha = 0.2f),
                        modifier = Modifier
                            .clickable { onToggleMode(!isAdminMode) }
                            .testTag("toggle_portal_mode_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isAdminMode) Icons.Default.AdminPanelSettings else Icons.Default.Language,
                                contentDescription = null,
                                tint = if (isAdminMode) AmberGoldLight else EmeraldTealLight,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = if (isAdminMode) "Admin CMS" else "Public Site",
                                color = if (isAdminMode) AmberGoldLight else EmeraldTealLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Role selector dropdown in Admin Mode
                    if (isAdminMode) {
                        Box {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = CorporateNavySurface,
                                modifier = Modifier
                                    .clickable { showRoleMenu = true }
                                    .testTag("role_selector_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(EmeraldTealLight)
                                    )
                                    Text(
                                        text = currentUserRole.title.split(" ").first(),
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Select Role",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = showRoleMenu,
                                onDismissRequest = { showRoleMenu = false },
                                modifier = Modifier.background(CorporateNavySurface)
                            ) {
                                Text(
                                    text = "SELECT PERMISSION ROLE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldTealLight,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                                UserRole.entries.filter { it != UserRole.GUEST }.forEach { role ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(
                                                    text = role.title,
                                                    fontWeight = if (role == currentUserRole) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (role == currentUserRole) EmeraldTealLight else Color.White,
                                                    fontSize = 13.sp
                                                )
                                                Text(
                                                    text = role.description,
                                                    color = Color(0xFF94A3B8),
                                                    fontSize = 10.sp
                                                )
                                            }
                                        },
                                        onClick = {
                                            onRoleChange(role)
                                            showRoleMenu = false
                                        },
                                        modifier = Modifier.testTag("role_item_${role.name}")
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
