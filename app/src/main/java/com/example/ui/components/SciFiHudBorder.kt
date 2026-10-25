package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DeepBlueAccent

@Composable
fun SciFiHudPanel(
    modifier: Modifier = Modifier,
    glowColor: Color = CyanGlow,
    borderColor: Color = DarkBorder,
    backgroundColor: Color = DarkSurface,
    cutCornerSize: Dp = 12.dp,
    showCornerTicks: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        glowColor.copy(alpha = 0.5f),
                        borderColor,
                        glowColor.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .drawWithContent {
                drawContent()
                if (showCornerTicks) {
                    val strokePx = 2.dp.toPx()
                    val tickLen = 10.dp.toPx()
                    val w = size.width
                    val h = size.height

                    // Top Left Corner Bracket
                    drawLine(
                        color = glowColor,
                        start = Offset(0f, tickLen),
                        end = Offset(0f, 0f),
                        strokeWidth = strokePx
                    )
                    drawLine(
                        color = glowColor,
                        start = Offset(0f, 0f),
                        end = Offset(tickLen, 0f),
                        strokeWidth = strokePx
                    )

                    // Top Right Corner Bracket
                    drawLine(
                        color = glowColor,
                        start = Offset(w - tickLen, 0f),
                        end = Offset(w, 0f),
                        strokeWidth = strokePx
                    )
                    drawLine(
                        color = glowColor,
                        start = Offset(w, 0f),
                        end = Offset(w, tickLen),
                        strokeWidth = strokePx
                    )

                    // Bottom Left Corner Bracket
                    drawLine(
                        color = glowColor,
                        start = Offset(0f, h - tickLen),
                        end = Offset(0f, h),
                        strokeWidth = strokePx
                    )
                    drawLine(
                        color = glowColor,
                        start = Offset(0f, h),
                        end = Offset(tickLen, h),
                        strokeWidth = strokePx
                    )

                    // Bottom Right Corner Bracket
                    drawLine(
                        color = glowColor,
                        start = Offset(w - tickLen, h),
                        end = Offset(w, h),
                        strokeWidth = strokePx
                    )
                    drawLine(
                        color = glowColor,
                        start = Offset(w, h - tickLen),
                        end = Offset(w, h),
                        strokeWidth = strokePx
                    )
                }
            }
            .padding(12.dp),
        content = content
    )
}
