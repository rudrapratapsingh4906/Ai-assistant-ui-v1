package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.ElectricPurple
import kotlin.math.sin

@Composable
fun AudioWaveformSpectrum(
    modifier: Modifier = Modifier,
    isListening: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "audio_spectrum")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing)
        ),
        label = "spectrum_phase"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        val barCount = 32
        val spacing = size.width / barCount
        val h = size.height

        for (i in 0 until barCount) {
            val factor = if (isListening) {
                (sin(phase + i * 0.3f) * 0.5f + 0.5f).coerceIn(0.2f, 1.0f)
            } else {
                0.2f
            }

            val barHeight = h * factor
            val x = i * spacing + spacing * 0.2f
            val y = (h - barHeight) / 2f
            val barColor = when {
                i % 3 == 0 -> CyanGlow
                i % 3 == 1 -> DeepBlueAccent
                else -> ElectricPurple
            }

            drawRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(spacing * 0.6f, barHeight)
            )
        }
    }
}
