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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TaskEntity
import com.example.ui.components.DivisionBadge
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminTasksScreen(
    taskList: List<TaskEntity>,
    onToggleTask: (TaskEntity) -> Unit,
    onAddTask: (title: String, desc: String, division: String, priority: String, dueDate: String) -> Unit,
    onDeleteTask: (TaskEntity) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_tasks_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Operational Tasks & Mandates",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "${taskList.count { !it.isCompleted }} open tasks awaiting completion",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("admin_add_task_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Task", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(taskList, key = { it.id }) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("task_item_${task.id}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { onToggleTask(task) },
                            colors = CheckboxDefaults.colors(checkedColor = EmeraldTeal),
                            modifier = Modifier.testTag("task_checkbox_${task.id}")
                        )

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = task.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                color = if (task.isCompleted) SlateTextMuted else MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = task.description,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 17.sp
                            )

                            Row(
                                modifier = Modifier.padding(top = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                DivisionBadge(division = task.assignedDivision)
                                StatusBadge(status = task.priority)
                                Text(text = "Due: ${task.dueDate}", fontSize = 10.sp, color = SlateTextMuted)
                            }
                        }

                        IconButton(onClick = { onDeleteTask(task) }) {
                            Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = SlateTextMuted, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AddTaskDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { t, d, div, pr, due ->
                    onAddTask(t, d, div, pr, due)
                    showAddDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, desc: String, division: String, priority: String, dueDate: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("Oct 15, 2026") }

    val divisions = listOf("Operations", "Technology", "Marketing", "Finance", "Business Development", "Customer Support", "Leadership")
    var selectedDiv by remember { mutableStateOf(divisions.first()) }
    var divExpanded by remember { mutableStateOf(false) }

    val priorities = listOf("Urgent", "High", "Normal")
    var selectedPriority by remember { mutableStateOf(priorities[1]) }

    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Assign Operational Task", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Task Title *") }, modifier = Modifier.fillMaxWidth().testTag("add_task_title"))
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Task Description *") }, minLines = 2, modifier = Modifier.fillMaxWidth())

                ExposedDropdownMenuBox(
                    expanded = divExpanded,
                    onExpandedChange = { divExpanded = !divExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedDiv,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Assigned Division") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = divExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = divExpanded,
                        onDismissRequest = { divExpanded = false }
                    ) {
                        divisions.forEach { d ->
                            DropdownMenuItem(text = { Text(d) }, onClick = { selectedDiv = d; divExpanded = false })
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    priorities.forEach { p ->
                        FilterChip(
                            selected = selectedPriority == p,
                            onClick = { selectedPriority = p },
                            label = { Text(p, fontSize = 11.sp) }
                        )
                    }
                }

                OutlinedTextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Target Due Date") }, modifier = Modifier.fillMaxWidth())

                if (error != null) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isBlank() || desc.isBlank()) {
                        error = "Please fill in task title and description"
                        return@Button
                    }
                    onAdd(title, desc, selectedDiv, selectedPriority, dueDate)
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTeal),
                modifier = Modifier.testTag("confirm_add_task_button")
            ) {
                Text("Assign Task", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
