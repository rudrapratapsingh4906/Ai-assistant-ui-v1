package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PerformanceGraphCard
import com.example.ui.components.SciFiHudPanel
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.MatrixGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.JarvisViewModel

@Composable
fun SystemScreen(
    viewModel: JarvisViewModel,
    modifier: Modifier = Modifier
) {
    val status by viewModel.systemStatus.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column {
            Text(
                text = "HARDWARE MONITOR & TELEMETRY",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    letterSpacing = 1.2.sp
                )
            )
            Text(
                text = "Real-time Sensor Feeds & Processor Clock Speeds",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                PerformanceGraphCard(
                    cpuPercent = status.cpuUsagePercent,
                    ramPercent = status.ramUsagePercent,
                    storagePercent = status.storageUsagePercent
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SciFiHudPanel(
                    modifier = Modifier.fillMaxWidth(),
                    glowColor = CyanGlow
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "PROCESSOR & CLOCK ARCHITECTURE",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontSize = 12.sp
                            )
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Core 0 - Core 3 (EFFICIENCY)", style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                            Text(text = "1.80 GHz • NORMAL", style = MaterialTheme.typography.bodySmall.copy(color = MatrixGreen, fontWeight = FontWeight.Bold))
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Core 4 - Core 7 (PERFORMANCE)", style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                            Text(text = "3.20 GHz • ACTIVE", style = MaterialTheme.typography.bodySmall.copy(color = CyanGlow, fontWeight = FontWeight.Bold))
                        }
                    }
                }

                SystemInfoCard(status = status)
            }
        }
    }
}
