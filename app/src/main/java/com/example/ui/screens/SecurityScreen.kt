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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SciFiHudPanel
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.MatrixGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.JarvisViewModel

@Composable
fun SecurityScreen(
    viewModel: JarvisViewModel,
    modifier: Modifier = Modifier
) {
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
                text = "SECURITY & PRIVACY CENTER",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    letterSpacing = 1.2.sp
                )
            )
            Text(
                text = "Zero-Trust Encryption & Sandbox Protection",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.weight(0.9f)) {
                SciFiHudPanel(
                    modifier = Modifier.fillMaxWidth(),
                    glowColor = MatrixGreen
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    ) {
                        Text(
                            text = "98 / 100",
                            style = MaterialTheme.typography.displayLarge.copy(
                                color = MatrixGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "PRIVACY & SECURITY HEALTH SCORE",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, fontSize = 10.sp)
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1.1f)) {
                SciFiHudPanel(
                    modifier = Modifier.fillMaxWidth(),
                    glowColor = CyanGlow
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "ACTIVE DEFENSES",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontSize = 12.sp
                            )
                        )

                        DefenseRow(title = "AES-256-GCM On-Device Vault", status = "ACTIVE • ENCRYPTED")
                        DefenseRow(title = "Offline First Local Processing", status = "ENABLED • SAFE")
                        DefenseRow(title = "Biometric Key Gate", status = "STANDBY")
                        DefenseRow(title = "App Check Attestation Token", status = "VERIFIED")
                    }
                }
            }
        }
    }
}

@Composable
private fun DefenseRow(title: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 11.sp))
        Text(text = status, style = MaterialTheme.typography.bodySmall.copy(color = MatrixGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp))
    }
}
