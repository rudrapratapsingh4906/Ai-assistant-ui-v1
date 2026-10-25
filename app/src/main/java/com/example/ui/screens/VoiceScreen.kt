package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AudioWaveformSpectrum
import com.example.ui.components.JarvisReactorCore
import com.example.ui.components.SciFiHudPanel
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.viewmodel.JarvisViewModel

@Composable
fun VoiceScreen(
    viewModel: JarvisViewModel,
    modifier: Modifier = Modifier
) {
    var isListening by remember { mutableStateOf(true) }
    var spokenText by remember { mutableStateOf("JARVIS, analyze memory allocation and run system optimization.") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "VOICE ASSISTANT MODE",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = CyanGlow,
                fontSize = 18.sp,
                letterSpacing = 2.sp
            )
        )

        Text(
            text = "Direct Acoustic Command Terminal",
            style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 12.sp
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        JarvisReactorCore(
            isSpeaking = isListening,
            onMicClick = { isListening = !isListening }
        )

        Spacer(modifier = Modifier.height(32.dp))

        SciFiHudPanel(
            modifier = Modifier.fillMaxWidth(),
            glowColor = CyanGlow
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isListening) "TRANSCRIBING SPEECH INPUT..." else "SPEECH RECOGNITION PAUSED",
                    style = MaterialTheme.typography.labelSmall.copy(color = CyanAccent, fontSize = 10.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "\"$spokenText\"",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                AudioWaveformSpectrum(isListening = isListening)
            }
        }
    }
}
