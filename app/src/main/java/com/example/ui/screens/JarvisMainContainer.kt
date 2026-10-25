package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HudScreen
import com.example.ui.components.JarvisFooter
import com.example.ui.components.JarvisSidebar
import com.example.ui.components.JarvisTopBar
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DeepBlueAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.viewmodel.JarvisViewModel

@Composable
fun JarvisMainContainer(
    viewModel: JarvisViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    // Handle System Back Button: return to Dashboard if on a sub-screen
    if (currentScreen != HudScreen.HOME) {
        BackHandler {
            viewModel.navigateTo(HudScreen.HOME)
        }
    }

    Scaffold(
        topBar = {
            JarvisTopBar(
                onSettingsClick = { viewModel.navigateTo(HudScreen.SETTINGS) }
            )
        },
        bottomBar = {
            Column {
                JarvisFooter()
            }
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val isWideScreen = maxWidth > 720.dp

            if (isWideScreen) {
                // Expanded Screen: Sidebar on Left, Screen Content on Right
                Row(modifier = Modifier.fillMaxSize()) {
                    JarvisSidebar(
                        currentScreen = currentScreen,
                        onScreenSelect = { viewModel.navigateTo(it) }
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        ScreenContent(currentScreen = currentScreen, viewModel = viewModel, isExpanded = true)
                    }
                }
            } else {
                // Compact Screen: Content with Bottom Navigation Bar
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {
                        ScreenContent(currentScreen = currentScreen, viewModel = viewModel, isExpanded = false)
                    }

                    // Compact Mobile Bottom Navigation Bar
                    NavigationBar(
                        containerColor = DarkSurface,
                        contentColor = TextPrimary,
                        tonalElevation = 8.dp
                    ) {
                        val mobileTabs = listOf(
                            HudScreen.HOME,
                            HudScreen.CHAT,
                            HudScreen.VOICE,
                            HudScreen.TOOLS,
                            HudScreen.SETTINGS
                        )

                        mobileTabs.forEach { screen ->
                            val selected = screen == currentScreen
                            val icon = when (screen) {
                                HudScreen.HOME -> Icons.Default.Dashboard
                                HudScreen.CHAT -> Icons.Default.ChatBubble
                                HudScreen.VOICE -> Icons.Default.Mic
                                HudScreen.TOOLS -> Icons.Default.Apps
                                HudScreen.SETTINGS -> Icons.Default.Settings
                                else -> Icons.Default.Dashboard
                            }

                            NavigationBarItem(
                                selected = selected,
                                onClick = { viewModel.navigateTo(screen) },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = screen.title,
                                        tint = if (selected) CyanGlow else TextMuted
                                    )
                                },
                                label = {
                                    Text(
                                        text = screen.title,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (selected) CyanGlow else TextMuted,
                                            fontSize = 9.sp
                                        )
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = DeepBlueAccent.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier.testTag("bottom_nav_${screen.tag}")
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScreenContent(
    currentScreen: HudScreen,
    viewModel: JarvisViewModel,
    isExpanded: Boolean
) {
    when (currentScreen) {
        HudScreen.HOME -> DashboardScreen(viewModel = viewModel, isExpandedScreen = isExpanded)
        HudScreen.CHAT -> ChatScreen(viewModel = viewModel)
        HudScreen.VOICE -> VoiceScreen(viewModel = viewModel)
        HudScreen.TOOLS -> ToolsScreen(viewModel = viewModel)
        HudScreen.FILES -> FilesScreen(viewModel = viewModel)
        HudScreen.AUTOMATION -> AutomationScreen(viewModel = viewModel)
        HudScreen.SYSTEM -> SystemScreen(viewModel = viewModel)
        HudScreen.SECURITY -> SecurityScreen(viewModel = viewModel)
        HudScreen.SETTINGS -> SettingsScreen(viewModel = viewModel)
    }
}
