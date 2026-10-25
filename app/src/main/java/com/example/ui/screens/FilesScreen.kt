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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FileItemData
import com.example.data.FileType
import com.example.ui.components.SciFiHudPanel
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.MatrixGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningOrange
import com.example.viewmodel.JarvisViewModel

@Composable
fun FilesScreen(
    viewModel: JarvisViewModel,
    modifier: Modifier = Modifier
) {
    val files by viewModel.files.collectAsState()
    val status by viewModel.systemStatus.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "ENCRYPTED FILE MANAGER",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                fontSize = 16.sp,
                letterSpacing = 1.2.sp
            )
        )
        Text(
            text = "Storage: ${status.storageUsedGb.toInt()} GB / ${status.storageTotalGb.toInt()} GB",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Storage Usage Gauge Bar
        SciFiHudPanel(
            modifier = Modifier.fillMaxWidth(),
            glowColor = CyanGlow
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "INTERNAL NVME STORAGE", style = MaterialTheme.typography.labelSmall.copy(color = CyanGlow, fontSize = 10.sp))
                    Text(text = "68% USED", style = MaterialTheme.typography.labelSmall.copy(color = CyanAccent, fontSize = 10.sp))
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { 0.68f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = CyanGlow,
                    trackColor = DarkBorder
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Files List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(files) { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(DarkSurface)
                        .border(1.dp, DarkBorder, RoundedCornerShape(6.dp))
                        .clickable { }
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    when (file.type) {
                                        FileType.SYSTEM -> WarningOrange.copy(alpha = 0.2f)
                                        FileType.MEDIA -> ElectricPurple.copy(alpha = 0.2f)
                                        FileType.CODE -> CyanGlow.copy(alpha = 0.2f)
                                        FileType.ARCHIVE -> MatrixGreen.copy(alpha = 0.2f)
                                        else -> DeepBlueAccent.copy(alpha = 0.2f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (file.type) {
                                    FileType.SYSTEM -> Icons.Default.Settings
                                    FileType.MEDIA -> Icons.Default.Image
                                    FileType.CODE -> Icons.Default.Code
                                    FileType.ARCHIVE -> Icons.Default.FolderZip
                                    else -> Icons.Default.Description
                                },
                                contentDescription = file.name,
                                tint = TextPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Column {
                            Text(
                                text = file.name,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary,
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = "${file.path} • ${file.date}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMuted,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Text(
                        text = file.size,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }
    }
}
