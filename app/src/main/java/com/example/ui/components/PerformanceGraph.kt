package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import kotlin.math.sin

@Composable
fun PerformanceGraphCard(
    modifier: Modifier = Modifier,
    cpuPercent: Int = 18,
    ramPercent: Int = 42,
    storagePercent: Int = 68
) {
    val infiniteTransition = rememberInfiniteTransition(label = "graph_anim")

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        ),
        label = "phase"
    )

    SciFiHudPanel(
        modifier = modifier.fillMaxWidth(),
        glowColor = CyanGlow,
        borderColor = DarkBorder,
        backgroundColor = DarkSurface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AI PERFORMANCE",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "View performance details",
                    tint = TextMuted,
                    modifier = Modifier.height(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Graph Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
            ) {
                val w = size.width
                val h = size.height

                // Grid Lines
                val gridRows = 3
                for (i in 0..gridRows) {
                    val y = h * i / gridRows
                    drawLine(
                        color = DarkBorder.copy(alpha = 0.5f),
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Wave 1: CPU Line (Cyan)
                val cpuPath = Path()
                val cpuFillPath = Path()
                cpuFillPath.moveTo(0f, h)

                for (x in 0..100) {
                    val xPx = w * (x / 100f)
                    val normVal = (cpuPercent / 100f) + 0.12f * sin(phase + x * 0.1f).toFloat()
                    val yPx = (h * (1f - normVal.coerceIn(0.1f, 0.9f)))
                    if (x == 0) {
                        cpuPath.moveTo(xPx, yPx)
                        cpuFillPath.lineTo(xPx, yPx)
                    } else {
                        cpuPath.lineTo(xPx, yPx)
                        cpuFillPath.lineTo(xPx, yPx)
                    }
                }
                cpuFillPath.lineTo(w, h)
                cpuFillPath.close()

                drawPath(
                    path = cpuFillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(CyanGlow.copy(alpha = 0.25f), Color.Transparent)
                    )
                )

                drawPath(
                    path = cpuPath,
                    color = CyanGlow,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Wave 2: RAM Line (Purple)
                val ramPath = Path()
                for (x in 0..100) {
                    val xPx = w * (x / 100f)
                    val normVal = (ramPercent / 100f) + 0.15f * sin(phase * 1.3f + x * 0.12f).toFloat()
                    val yPx = (h * (1f - normVal.coerceIn(0.1f, 0.95f)))
                    if (x == 0) {
                        ramPath.moveTo(xPx, yPx)
                    } else {
                        ramPath.lineTo(xPx, yPx)
                    }
                }

                drawPath(
                    path = ramPath,
                    color = ElectricPurple,
                    style = Stroke(width = 2.dp.toPx())
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Metrics Legend Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricItem(label = "CPU", value = "$cpuPercent%", color = CyanGlow)
                MetricItem(label = "RAM", value = "$ramPercent%", color = ElectricPurple)
                MetricItem(label = "Storage", value = "$storagePercent%", color = DeepBlueAccent)
            }
        }
    }
}

@Composable
private fun MetricItem(
    label: String,
    value: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 10.sp
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 16.sp
            )
        )
    }
}
