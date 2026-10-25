package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ConversationItem
import com.example.data.HudScreen
import com.example.data.TaskItem
import com.example.data.TaskState
import com.example.ui.components.AudioWaveformSpectrum
import com.example.ui.components.CyberGlobeView
import com.example.ui.components.JarvisReactorCore
import com.example.ui.components.PerformanceGraphCard
import com.example.ui.components.SciFiHudPanel
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.MatrixGreen
import com.example.ui.theme.TextAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningOrange
import com.example.viewmodel.JarvisViewModel

@Composable
fun DashboardScreen(
    viewModel: JarvisViewModel,
    isExpandedScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    val status by viewModel.systemStatus.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val conversations by viewModel.recentConversations.collectAsState()
    val voiceSettings by viewModel.voiceAiSettings.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isExpandedScreen) {
            // Wide Screen Multi-Column Desktop Dashboard matching original image layout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Column (System Status & Performance)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SystemStatusCard(status = status)
                    PerformanceGraphCard(
                        cpuPercent = status.cpuUsagePercent,
                        ramPercent = status.ramUsagePercent,
                        storagePercent = status.storageUsagePercent
                    )
                }

                // Center Column (JARVIS Reactor Core Hero)
                Column(
                    modifier = Modifier.weight(1.3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    JarvisReactorCore(
                        onMicClick = { viewModel.navigateTo(HudScreen.VOICE) }
                    )
                }

                // Right Column (Quick Actions & Current Tasks)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QuickActionsCard(onNavigate = { viewModel.navigateTo(it) })
                    CurrentTasksCard(
                        tasks = tasks,
                        onTaskClick = { viewModel.toggleTaskState(it) }
                    )
                }
            }

            // Bottom Grid (Recent Conversations, Globe, System Info, Voice & AI)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Bottom Left: Recent Conversations
                Box(modifier = Modifier.weight(1.1f)) {
                    RecentConversationsCard(
                        conversations = conversations,
                        onOpenChat = { viewModel.navigateTo(HudScreen.CHAT) }
                    )
                }

                // Bottom Center: Cyber Globe
                Box(modifier = Modifier.weight(1f)) {
                    CyberGlobeView()
                }

                // Bottom Center-Right: System Info
                Box(modifier = Modifier.weight(1.1f)) {
                    SystemInfoCard(status = status)
                }

                // Bottom Right: Voice & AI
                Box(modifier = Modifier.weight(1.1f)) {
                    VoiceAiCard(
                        settings = voiceSettings,
                        onWakeWordToggle = { viewModel.toggleWakeWord(it) },
                        onVoiceResponseToggle = { viewModel.toggleVoiceResponse(it) },
                        onOfflineModeToggle = { viewModel.toggleOfflineMode(it) },
                        onLocalProcessingToggle = { viewModel.toggleLocalProcessing(it) }
                    )
                }
            }
        } else {
            // Mobile Compact Vertical Layout
            JarvisReactorCore(
                onMicClick = { viewModel.navigateTo(HudScreen.VOICE) }
            )

            QuickActionsCard(onNavigate = { viewModel.navigateTo(it) })

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SystemStatusCard(status = status)
                }
                Box(modifier = Modifier.weight(1f)) {
                    PerformanceGraphCard(
                        cpuPercent = status.cpuUsagePercent,
                        ramPercent = status.ramUsagePercent,
                        storagePercent = status.storageUsagePercent
                    )
                }
            }

            CurrentTasksCard(
                tasks = tasks,
                onTaskClick = { viewModel.toggleTaskState(it) }
            )

            CyberGlobeView()

            RecentConversationsCard(
                conversations = conversations,
                onOpenChat = { viewModel.navigateTo(HudScreen.CHAT) }
            )

            SystemInfoCard(status = status)

            VoiceAiCard(
                settings = voiceSettings,
                onWakeWordToggle = { viewModel.toggleWakeWord(it) },
                onVoiceResponseToggle = { viewModel.toggleVoiceResponse(it) },
                onOfflineModeToggle = { viewModel.toggleOfflineMode(it) },
                onLocalProcessingToggle = { viewModel.toggleLocalProcessing(it) }
            )
        }
    }
}

@Composable
fun SystemStatusCard(status: com.example.data.SystemStatusData) {
    SciFiHudPanel(
        modifier = Modifier.fillMaxWidth(),
        glowColor = CyanGlow
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "SYSTEM STATUS",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            )

            StatusRow(
                icon = Icons.Default.Apps,
                label = "AI Core",
                value = if (status.aiCoreActive) "Active •" else "Standby",
                valueColor = MatrixGreen
            )

            StatusRow(
                icon = Icons.Default.RadioButtonUnchecked,
                label = "Model",
                value = status.modelName,
                valueColor = TextPrimary
            )

            StatusRow(
                icon = Icons.Default.Router,
                label = "Network",
                value = if (status.networkConnected) "Connected •" else "Offline",
                valueColor = MatrixGreen
            )

            StatusRow(
                icon = Icons.Default.Storage,
                label = "Storage",
                value = "${status.storageUsedGb.toInt()} GB / ${status.storageTotalGb.toInt()} GB",
                valueColor = TextSecondary
            )

            StatusRow(
                icon = Icons.Default.BatteryChargingFull,
                label = "Battery",
                value = "${status.batteryPercent}% - ${if (status.isCharging) "Charging" else "Discharging"}",
                valueColor = CyanAccent
            )
        }
    }
}

@Composable
private fun StatusRow(
    icon: ImageVector,
    label: String,
    value: String,
    valueColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = CyanGlow,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = valueColor,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
fun QuickActionsCard(
    onNavigate: (HudScreen) -> Unit
) {
    SciFiHudPanel(
        modifier = Modifier.fillMaxWidth(),
        glowColor = CyanGlow
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "QUICK ACTIONS",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            )

            QuickActionRow(
                title = "Open Chat",
                subtitle = "Start conversation",
                icon = Icons.Default.ChatBubble,
                badgeColor = DeepBlueAccent,
                onClick = { onNavigate(HudScreen.CHAT) },
                testTag = "quick_open_chat"
            )

            QuickActionRow(
                title = "Voice Assistant",
                subtitle = "Tap to speak",
                icon = Icons.Default.Mic,
                badgeColor = ElectricPurple,
                onClick = { onNavigate(HudScreen.VOICE) },
                testTag = "quick_voice_assistant"
            )

            QuickActionRow(
                title = "File Manager",
                subtitle = "Manage your files",
                icon = Icons.Default.Folder,
                badgeColor = CyanGlow,
                onClick = { onNavigate(HudScreen.FILES) },
                testTag = "quick_file_manager"
            )

            QuickActionRow(
                title = "App Launcher",
                subtitle = "Open applications",
                icon = Icons.Default.Apps,
                badgeColor = WarningOrange,
                onClick = { onNavigate(HudScreen.TOOLS) },
                testTag = "quick_app_launcher"
            )

            QuickActionRow(
                title = "Automation",
                subtitle = "Smart tasks",
                icon = Icons.Default.Settings,
                badgeColor = ElectricPurple,
                onClick = { onNavigate(HudScreen.AUTOMATION) },
                testTag = "quick_automation"
            )
        }
    }
}

@Composable
private fun QuickActionRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badgeColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(DarkSurface)
            .border(1.dp, DarkBorder, RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .testTag(testTag),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(badgeColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = badgeColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Navigate",
            tint = TextMuted,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun CurrentTasksCard(
    tasks: List<TaskItem>,
    onTaskClick: (String) -> Unit
) {
    SciFiHudPanel(
        modifier = Modifier.fillMaxWidth(),
        glowColor = CyanGlow
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CURRENT TASKS",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CyanGlow,
                        fontSize = 11.sp
                    )
                )
            }

            tasks.forEach { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTaskClick(task.id) }
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary,
                                    fontSize = 12.sp
                                )
                            )
                            if (task.progressPercent > 0f && task.progressPercent < 1f) {
                                Text(
                                    text = "${(task.progressPercent * 100).toInt()}%",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = CyanGlow,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                        Text(
                            text = task.statusText,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = when (task.state) {
                                    TaskState.RUNNING -> CyanGlow
                                    TaskState.COMPLETED -> MatrixGreen
                                    TaskState.WAITING -> WarningOrange
                                    TaskState.PENDING -> TextMuted
                                },
                                fontSize = 10.sp
                            )
                        )
                        if (task.state == TaskState.RUNNING) {
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { task.progressPercent },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp),
                                color = CyanGlow,
                                trackColor = DarkBorder
                            )
                        }
                    }

                    Icon(
                        imageVector = when (task.state) {
                            TaskState.RUNNING -> Icons.Default.Pause
                            TaskState.WAITING -> Icons.Default.PlayArrow
                            TaskState.COMPLETED -> Icons.Default.CheckCircle
                            TaskState.PENDING -> Icons.Default.RadioButtonUnchecked
                        },
                        contentDescription = "Task State",
                        tint = when (task.state) {
                            TaskState.RUNNING -> CyanGlow
                            TaskState.COMPLETED -> MatrixGreen
                            TaskState.WAITING -> WarningOrange
                            TaskState.PENDING -> TextMuted
                        },
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RecentConversationsCard(
    conversations: List<ConversationItem>,
    onOpenChat: () -> Unit
) {
    SciFiHudPanel(
        modifier = Modifier.fillMaxWidth(),
        glowColor = CyanGlow
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RECENT CONVERSATIONS",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CyanGlow,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.clickable(onClick = onOpenChat)
                )
            }

            conversations.forEach { conv ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .clickable(onClick = onOpenChat)
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    when (conv.category) {
                                        "Planning" -> DeepBlueAccent.copy(alpha = 0.3f)
                                        "Creative" -> ElectricPurple.copy(alpha = 0.3f)
                                        "Maintenance" -> MatrixGreen.copy(alpha = 0.3f)
                                        else -> WarningOrange.copy(alpha = 0.3f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (conv.category) {
                                    "Planning" -> Icons.Default.ChatBubble
                                    "Creative" -> Icons.Default.Image
                                    "Maintenance" -> Icons.Default.Apps
                                    else -> Icons.Default.Description
                                },
                                contentDescription = conv.title,
                                tint = TextPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Column {
                            Text(
                                text = conv.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary,
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = conv.snippet,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMuted,
                                    fontSize = 10.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Text(
                        text = conv.timeLabel,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SystemInfoCard(status: com.example.data.SystemStatusData) {
    SciFiHudPanel(
        modifier = Modifier.fillMaxWidth(),
        glowColor = CyanGlow
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "SYSTEM INFO",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            )

            InfoRow(label = "Device", value = status.deviceModel)
            InfoRow(label = "Android Version", value = status.androidVersion)
            InfoRow(label = "Model", value = status.modelNumber)
            InfoRow(label = "RAM", value = "${status.totalRamGb} GB (${status.freeRamGb} GB free)")
            InfoRow(label = "Storage", value = "${status.storageTotalGb.toInt()} GB (${status.storageUsedGb.toInt()} GB free)")
            InfoRow(label = "Temperature", value = "${status.systemTemperatureC}°C (Normal)")
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun VoiceAiCard(
    settings: com.example.data.VoiceAiSettings,
    onWakeWordToggle: (Boolean) -> Unit,
    onVoiceResponseToggle: (Boolean) -> Unit,
    onOfflineModeToggle: (Boolean) -> Unit,
    onLocalProcessingToggle: (Boolean) -> Unit
) {
    SciFiHudPanel(
        modifier = Modifier.fillMaxWidth(),
        glowColor = CyanGlow
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VOICE & AI",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                )

                Text(
                    text = "• Listening Ready",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MatrixGreen,
                        fontSize = 10.sp
                    )
                )
            }

            AudioWaveformSpectrum()

            ToggleRow(label = "Wake Word", checked = settings.wakeWordEnabled, onCheckedChange = onWakeWordToggle)
            ToggleRow(label = "Voice Response", checked = settings.voiceResponseEnabled, onCheckedChange = onVoiceResponseToggle)
            ToggleRow(label = "Offline Mode", checked = settings.offlineModeEnabled, onCheckedChange = onOfflineModeToggle)
            ToggleRow(label = "Local Processing", checked = settings.localProcessingEnabled, onCheckedChange = onLocalProcessingToggle)
        }
    }
}

@Composable
private fun ToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(color = TextPrimary, fontSize = 11.sp)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = DarkBackground,
                checkedTrackColor = CyanGlow,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = DarkSurface
            ),
            modifier = Modifier.height(24.dp)
        )
    }
}
