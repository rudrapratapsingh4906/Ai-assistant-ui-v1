package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HudScreen
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

@Composable
fun JarvisTopBar(
    onSettingsClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBackground)
            .drawBehind {
                drawLine(
                    color = DarkBorder,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Logo + App Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(CyanGlow.copy(alpha = 0.4f), DarkBackground)
                        )
                    )
                    .border(1.5.dp, CyanGlow, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "Universal AI Assistant Logo",
                    tint = CyanGlow,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                    text = "UNIVERSAL AI ASSISTANT",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        letterSpacing = 1.5.sp
                    )
                )
                Text(
                    text = "Your Personal AI Companion",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                )
            }
        }

        // Center Quote Speech Bubble
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(DarkSurfaceVariant)
                .border(1.dp, CyanGlow.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "\"Hello, I'm RAYA, your personal AI assistant. How can I help you today?\"",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = CyanAccent,
                    fontSize = 11.sp
                )
            )
        }

        // Right Status Telemetry
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MatrixGreen)
                )
                Text(
                    text = "Online",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                )
            }

            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = "Wi-Fi Connected",
                tint = TextSecondary,
                modifier = Modifier.size(16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.BatteryChargingFull,
                    contentDescription = "Battery 78%",
                    tint = CyanGlow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "78%",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                )
            }

            Text(
                text = "10:24",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            )

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = TextSecondary,
                modifier = Modifier
                    .size(18.dp)
                    .clickable(onClick = onSettingsClick)
                    .testTag("top_bar_settings_icon")
            )
        }
    }
}

@Composable
fun JarvisSidebar(
    currentScreen: HudScreen,
    onScreenSelect: (HudScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(185.dp)
            .fillMaxHeight()
            .background(DarkBackground)
            .drawBehind {
                drawLine(
                    color = DarkBorder,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            HudScreen.entries.forEach { screen ->
                val selected = screen == currentScreen
                val icon = when (screen) {
                    HudScreen.HOME -> Icons.Default.Dashboard
                    HudScreen.CHAT -> Icons.Default.ChatBubble
                    HudScreen.VOICE -> Icons.Default.Mic
                    HudScreen.TOOLS -> Icons.Default.Apps
                    HudScreen.FILES -> Icons.Default.Folder
                    HudScreen.AUTOMATION -> Icons.Default.SmartToy
                    HudScreen.SYSTEM -> Icons.Default.Settings
                    HudScreen.SECURITY -> Icons.Default.Security
                    HudScreen.SETTINGS -> Icons.Default.Settings
                }

                SidebarItemRow(
                    screen = screen,
                    icon = icon,
                    isSelected = selected,
                    onClick = { onScreenSelect(screen) }
                )
            }
        }

        // Bottom Offline First Notice
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Offline First",
                    tint = CyanGlow,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "Offline First",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                )
            }
            Text(
                text = "Your data stays on your device",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
                    fontSize = 9.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            AudioWaveformSpectrum()
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Privacy • Security • Freedom",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontSize = 9.sp
                )
            )
        }
    }
}

@Composable
private fun SidebarItemRow(
    screen: HudScreen,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) {
                    Brush.horizontalGradient(
                        colors = listOf(DeepBlueAccent.copy(alpha = 0.6f), DarkSurface)
                    )
                } else {
                    Brush.linearGradient(colors = listOf(Color.Transparent, Color.Transparent))
                }
            )
            .border(
                width = 1.dp,
                color = if (isSelected) CyanGlow else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .testTag("nav_item_${screen.tag}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(if (isSelected) CyanGlow else DarkSurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = screen.title,
                tint = if (isSelected) DarkBackground else TextSecondary,
                modifier = Modifier.size(15.dp)
            )
        }

        Column {
            Text(
                text = screen.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    color = if (isSelected) TextPrimary else TextSecondary,
                    fontSize = 12.sp
                )
            )
            Text(
                text = screen.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
                    fontSize = 9.sp
                )
            )
        }
    }
}

@Composable
fun JarvisFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBackground)
            .drawBehind {
                drawLine(
                    color = DarkBorder,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "RAYA INTERFACE v1.0.0",
            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 10.sp)
        )
        Text(
            text = "INTELLIGENCE / PRIVACY / CONTROL",
            style = MaterialTheme.typography.labelSmall.copy(color = CyanGlow, fontSize = 10.sp, letterSpacing = 1.sp)
        )
        Text(
            text = "UNIVERSAL AI ASSISTANT",
            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 10.sp)
        )
    }
}
