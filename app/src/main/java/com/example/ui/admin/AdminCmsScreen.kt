package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AnnouncementEntity
import com.example.data.ContentBlockEntity
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun AdminCmsScreen(
    currentUserRole: UserRole,
    contentBlocks: List<ContentBlockEntity>,
    announcements: List<AnnouncementEntity>,
    onUpdateContent: (key: String, title: String, subtitle: String, body: String) -> Unit,
    onAddAnnouncement: (title: String, category: String, summary: String, content: String, isPublic: Boolean) -> Unit
) {
    if (!currentUserRole.canAccessCMS()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(40.dp))
                    Text("Website CMS Access Restricted", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF991B1B))
                    Text("Website management clearance is reserved for Founder/CEO, Technology, and Marketing divisions.", fontSize = 12.sp, color = Color(0xFF7F1D1D), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }
        }
        return
    }

    var editingBlock by remember { mutableStateOf<ContentBlockEntity?>(null) }
    var showAddAnnouncementDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_cms_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Website Content Management (CMS)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Publish live headlines, mission statements & announcements",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = { showAddAnnouncementDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("cms_new_announcement_button")
                ) {
                    Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Post News", fontSize = 12.sp)
                }
            }
        }

        item {
            Text(
                text = "Live Public Website Copy Blocks",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(contentBlocks, key = { it.key }) { block ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.testTag("cms_block_${block.key}")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = block.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = "Key: ${block.key}", fontSize = 11.sp, color = EmeraldTeal, fontWeight = FontWeight.SemiBold)
                        }
                        IconButton(onClick = { editingBlock = block }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Copy", tint = EmeraldTeal)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = block.body,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(10.dp),
                            lineHeight = 18.sp
                        )
                    }

                    Text(
                        text = "Last updated: ${block.lastUpdated} by ${block.updatedBy}",
                        fontSize = 10.sp,
                        color = SlateTextMuted
                    )
                }
            }
        }
    }

    // Edit CMS Block Dialog
    editingBlock?.let { block ->
        EditCmsBlockDialog(
            block = block,
            onDismiss = { editingBlock = null },
            onSave = { key, title, subtitle, body ->
                onUpdateContent(key, title, subtitle, body)
                editingBlock = null
            }
        )
    }

    // Add Announcement Dialog
    if (showAddAnnouncementDialog) {
        AddAnnouncementDialog(
            onDismiss = { showAddAnnouncementDialog = false },
            onPost = { t, c, s, cnt, pub ->
                onAddAnnouncement(t, c, s, cnt, pub)
                showAddAnnouncementDialog = false
            }
        )
    }
}

@Composable
fun EditCmsBlockDialog(
    block: ContentBlockEntity,
    onDismiss: () -> Unit,
    onSave: (key: String, title: String, subtitle: String, body: String) -> Unit
) {
    var title by remember { mutableStateOf(block.title) }
    var subtitle by remember { mutableStateOf(block.subtitle) }
    var body by remember { mutableStateOf(block.body) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Copy: ${block.key}", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = body, onValueChange = { body = it }, label = { Text("Content Body *") }, minLines = 4, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(block.key, title, subtitle, body) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
            ) {
                Text("Publish to Website", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnnouncementDialog(
    onDismiss: () -> Unit,
    onPost: (title: String, category: String, summary: String, content: String, isPublic: Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }

    val categories = listOf("Platform Update", "Expansion", "Milestone", "Press Release")
    var selectedCat by remember { mutableStateOf(categories.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Broadcast Announcement", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Headline *") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = summary, onValueChange = { summary = it }, label = { Text("Short Summary *") }, minLines = 2, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Full Announcement Body *") }, minLines = 3, modifier = Modifier.fillMaxWidth())
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isPublic, onCheckedChange = { isPublic = it }, colors = CheckboxDefaults.colors(checkedColor = EmeraldTeal))
                    Text("Publish to Public News & Updates page", fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && summary.isNotBlank()) {
                        onPost(title, selectedCat, summary, content, isPublic)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal)
            ) {
                Text("Broadcast", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
