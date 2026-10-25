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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatMessage
import com.example.data.ChatSender
import com.example.ui.components.SciFiHudPanel
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.MatrixGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.JarvisViewModel

@Composable
fun ChatScreen(
    viewModel: JarvisViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val highThinkingMode by viewModel.useHighThinkingMode.collectAsState()

    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // High Thinking Control Banner
        SciFiHudPanel(
            modifier = Modifier.fillMaxWidth(),
            glowColor = if (highThinkingMode) ElectricPurple else CyanGlow
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "Thinking Mode Engine",
                        tint = if (highThinkingMode) ElectricPurple else CyanGlow,
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = if (highThinkingMode) "GEMINI 4 ARGON (HIGH THINKING)" else "GEMINI 3.8 FLASH",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontSize = 12.sp,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = if (highThinkingMode) "Deep reasoning & multi-step cognitive processing" else "Fast conversational inference engine",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "High Thinking",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (highThinkingMode) ElectricPurple else TextMuted,
                            fontSize = 10.sp
                        )
                    )
                    Switch(
                        checked = highThinkingMode,
                        onCheckedChange = { viewModel.toggleHighThinkingMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DarkBackground,
                            checkedTrackColor = ElectricPurple
                        ),
                        modifier = Modifier.testTag("toggle_thinking_mode")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Prompt Preset Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PromptChip("Analyze System Health", onClick = { viewModel.sendChatMessage("Run complete system audit & diagnostic analysis.") })
            PromptChip("Optimize Code", onClick = { viewModel.sendChatMessage("Optimize my Kotlin architecture for minimum memory overhead.") })
            PromptChip("Project Plan", onClick = { viewModel.sendChatMessage("Generate step-by-step roadmap for JARVIS v2.0.") })
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Message History List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                MessageBubble(message = msg)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Input Field Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                placeholder = {
                    Text(
                        "Command JARVIS AI Core...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted, fontSize = 13.sp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanGlow,
                    unfocusedBorderColor = DarkBorder,
                    focusedContainerColor = DarkSurface,
                    unfocusedContainerColor = DarkSurface,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (inputText.isNotBlank()) CyanGlow else DarkSurfaceVariant)
                    .clickable(enabled = inputText.isNotBlank() && !isGenerating) {
                        viewModel.sendChatMessage(inputText)
                        inputText = ""
                    }
                    .testTag("send_chat_button"),
                contentAlignment = Alignment.Center
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = DarkBackground,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send Message",
                        tint = if (inputText.isNotBlank()) DarkBackground else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PromptChip(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DarkSurfaceVariant)
            .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                color = CyanAccent,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
private fun MessageBubble(message: ChatMessage) {
    val isUser = message.sender == ChatSender.USER

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (!isUser) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = "JARVIS AI",
                    tint = CyanGlow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = message.modelUsed,
                    style = MaterialTheme.typography.labelSmall.copy(color = CyanGlow, fontSize = 10.sp)
                )
            } else {
                Text(
                    text = "Operator",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 10.sp)
                )
            }
            Text(
                text = "• ${message.timestamp}",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 10.sp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Thinking process visualizer box
        if (!message.thinkingProcess.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(DarkBackground)
                    .border(1.dp, ElectricPurple.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Thinking",
                            tint = ElectricPurple,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "THINKING PROCESS (HIGH LEVEL)",
                            style = MaterialTheme.typography.labelSmall.copy(color = ElectricPurple, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = message.thinkingProcess,
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Message Content Box
        Box(
            modifier = Modifier
                .fillMaxWidth(if (isUser) 0.8f else 0.95f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isUser) DeepBlueAccent.copy(alpha = 0.3f) else DarkSurface)
                .border(
                    width = 1.dp,
                    color = if (isUser) DeepBlueAccent else DarkBorder,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text.ifBlank { "..." },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextPrimary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}
