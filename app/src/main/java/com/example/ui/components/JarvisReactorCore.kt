package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import kotlin.math.sin

@Composable
fun JarvisReactorCore(
    modifier: Modifier = Modifier,
    isSpeaking: Boolean = false,
    onMicClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "reactor_anim")

    // Rotating Ring Angles
    val rotation1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing)
        ),
        label = "rot1"
    )

    val rotation2 by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing)
        ),
        label = "rot2"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        ),
        label = "wave"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Reactor Circle Canvas Box
        Box(
            modifier = Modifier.size(240.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background Glow Arc Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val radius = size.minDimension / 2f - 10.dp.toPx()

                // Radial Ambient Glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            CyanGlow.copy(alpha = 0.35f),
                            DeepBlueAccent.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radius * 1.2f
                    )
                )

                // Outer Tech Track
                drawCircle(
                    color = CyanAccent.copy(alpha = 0.25f),
                    radius = radius,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Dashed Outer Segment
                drawArc(
                    color = CyanGlow,
                    startAngle = 0f,
                    sweepAngle = 120f,
                    useCenter = false,
                    style = Stroke(
                        width = 4.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                    )
                )

                drawArc(
                    color = ElectricPurple,
                    startAngle = 180f,
                    sweepAngle = 90f,
                    useCenter = false,
                    style = Stroke(width = 3.dp.toPx())
                )
            }

            // Outer Rotating Ring 1
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation1)
            ) {
                val radius = size.minDimension / 2f - 22.dp.toPx()
                val stroke = Stroke(width = 3.dp.toPx())
                drawArc(
                    color = CyanGlow.copy(alpha = 0.8f),
                    startAngle = 20f,
                    sweepAngle = 70f,
                    useCenter = false,
                    style = stroke
                )
                drawArc(
                    color = DeepBlueAccent.copy(alpha = 0.9f),
                    startAngle = 140f,
                    sweepAngle = 100f,
                    useCenter = false,
                    style = stroke
                )
                drawArc(
                    color = CyanGlow.copy(alpha = 0.6f),
                    startAngle = 270f,
                    sweepAngle = 60f,
                    useCenter = false,
                    style = stroke
                )
            }

            // Inner Counter-Rotating Ring 2
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation2)
            ) {
                val radius = size.minDimension / 2f - 38.dp.toPx()
                val stroke = Stroke(
                    width = 2.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                )
                drawCircle(
                    color = CyanAccent.copy(alpha = 0.5f),
                    radius = radius,
                    style = stroke
                )
            }

            // Center JARVIS Core Orb
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                DarkBackground,
                                Color(0xFF041226)
                            )
                        )
                    )
                    .border(
                        width = 2.dp,
                        brush = Brush.sweepGradient(
                            colors = listOf(CyanGlow, DeepBlueAccent, ElectricPurple, CyanGlow)
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "JARVIS Brain Core",
                        tint = CyanGlow,
                        modifier = Modifier.size(38.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "JARVIS",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontSize = 17.sp,
                            letterSpacing = 2.sp
                        )
                    )
                    Text(
                        text = "AI CORE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CyanAccent,
                            fontSize = 10.sp,
                            letterSpacing = 1.5.sp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Audio Waveform + Mic Control Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Left Waveform Bars
            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .height(32.dp)
            ) {
                val barCount = 14
                val spacing = size.width / barCount
                for (i in 0 until barCount) {
                    val heightFactor = sin(wavePhase + i * 0.45f).coerceIn(0.25f, 1.0f)
                    val barHeight = size.height * heightFactor * if (isSpeaking) 1.2f else 0.7f
                    val x = i * spacing + spacing / 4
                    val y = (size.height - barHeight) / 2
                    drawRect(
                        color = if (i % 2 == 0) CyanGlow else DeepBlueAccent,
                        topLeft = Offset(x, y),
                        size = Size(spacing * 0.5f, barHeight)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Microphone Interactive Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    DeepBlueAccent,
                                    DarkBackground
                                )
                            )
                        )
                        .border(
                            width = 2.dp,
                            color = if (isSpeaking) ElectricPurple else CyanGlow,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onMicClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Tap to speak mic button",
                        tint = TextPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (isSpeaking) "Listening..." else "Tap to speak",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isSpeaking) CyanGlow else TextMuted,
                        fontSize = 11.sp
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Right Waveform Bars
            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .height(32.dp)
            ) {
                val barCount = 14
                val spacing = size.width / barCount
                for (i in 0 until barCount) {
                    val heightFactor = sin(wavePhase + (i + 14) * 0.45f).coerceIn(0.25f, 1.0f)
                    val barHeight = size.height * heightFactor * if (isSpeaking) 1.2f else 0.7f
                    val x = i * spacing + spacing / 4
                    val y = (size.height - barHeight) / 2
                    drawRect(
                        color = if (i % 2 == 0) CyanGlow else ElectricPurple,
                        topLeft = Offset(x, y),
                        size = Size(spacing * 0.5f, barHeight)
                    )
                }
            }
        }
    }
}
