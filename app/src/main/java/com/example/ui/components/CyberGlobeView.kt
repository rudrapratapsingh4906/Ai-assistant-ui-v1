package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CyberGlobeView(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "globe_anim")

    val globeRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 16000, easing = LinearEasing)
        ),
        label = "globe_rot"
    )

    SciFiHudPanel(
        modifier = modifier.fillMaxWidth(),
        glowColor = CyanGlow,
        borderColor = DarkBorder,
        backgroundColor = DarkSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val radius = size.minDimension / 2f - 12.dp.toPx()

                    // Glow background
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                CyanGlow.copy(alpha = 0.3f),
                                DeepBlueAccent.copy(alpha = 0.15f),
                                Color.Transparent
                            ),
                            center = center,
                            radius = radius * 1.3f
                        )
                    )

                    // Outer Orbit Ring
                    drawCircle(
                        color = CyanAccent.copy(alpha = 0.4f),
                        radius = radius,
                        style = Stroke(
                            width = 1.5.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                    )

                    // Equator Arc
                    drawOval(
                        color = CyanGlow.copy(alpha = 0.7f),
                        topLeft = Offset(center.x - radius, center.y - radius * 0.35f),
                        size = Size(radius * 2f, radius * 0.7f),
                        style = Stroke(width = 1.5.dp.toPx())
                    )

                    // Tilted Latitude Arc 1
                    drawOval(
                        color = DeepBlueAccent.copy(alpha = 0.8f),
                        topLeft = Offset(center.x - radius * 0.85f, center.y - radius * 0.65f),
                        size = Size(radius * 1.7f, radius * 1.3f),
                        style = Stroke(width = 1.2.dp.toPx())
                    )

                    // Meridian Vertical Oval
                    drawOval(
                        color = CyanAccent.copy(alpha = 0.6f),
                        topLeft = Offset(center.x - radius * 0.4f, center.y - radius),
                        size = Size(radius * 0.8f, radius * 2f),
                        style = Stroke(width = 1.2.dp.toPx())
                    )

                    // Dynamic Orbiting Satellite Dots
                    val radAngle = Math.toRadians(globeRotation.toDouble())
                    val dotX = (center.x + radius * cos(radAngle)).toFloat()
                    val dotY = (center.y + radius * 0.35f * sin(radAngle)).toFloat()

                    drawCircle(
                        color = CyanGlow,
                        radius = 4.dp.toPx(),
                        center = Offset(dotX, dotY)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "UNIVERSAL AI ASSISTANT",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 13.sp,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Always with you",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
                    fontSize = 11.sp
                )
            )
        }
    }
}
