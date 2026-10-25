package com.example.data

import androidx.compose.ui.graphics.vector.ImageVector

enum class HudScreen(val title: String, val subtitle: String, val tag: String) {
    HOME("Home", "Dashboard", "dashboard"),
    CHAT("Chat", "AI Conversation", "chat"),
    VOICE("Voice", "Voice Assistant", "voice"),
    TOOLS("Tools", "Apps & Utilities", "tools"),
    FILES("Files", "File Manager", "files"),
    AUTOMATION("Automation", "Smart Tasks", "automation"),
    SYSTEM("System", "Monitor & Control", "system"),
    SECURITY("Security", "Privacy & Permissions", "security"),
    SETTINGS("Settings", "Customization", "settings")
}

data class SystemStatusData(
    val aiCoreActive: Boolean = true,
    val modelName: String = "Gemini 4 Argon",
    val thinkingLevel: String = "HIGH",
    val networkConnected: Boolean = true,
    val networkPingMs: Int = 18,
    val storageUsedGb: Double = 185.0,
    val storageTotalGb: Double = 216.0,
    val batteryPercent: Int = 78,
    val isCharging: Boolean = true,
    val cpuUsagePercent: Int = 18,
    val ramUsagePercent: Int = 42,
    val storageUsagePercent: Int = 68,
    val deviceModel: String = "OPPO Reno16 5G",
    val androidVersion: String = "16 (SDK 36)",
    val modelNumber: String = "CPH2865",
    val totalRamGb: Double = 7.1,
    val freeRamGb: Double = 1.6,
    val systemTemperatureC: Int = 32
)

data class TaskItem(
    val id: String,
    val title: String,
    val statusText: String,
    val progressPercent: Float, // 0.0 to 1.0
    val state: TaskState
)

enum class TaskState {
    RUNNING, WAITING, COMPLETED, PENDING
}

data class ConversationItem(
    val id: String,
    val title: String,
    val snippet: String,
    val timeLabel: String,
    val category: String
)

data class VoiceAiSettings(
    val wakeWordEnabled: Boolean = true,
    val voiceResponseEnabled: Boolean = true,
    val offlineModeEnabled: Boolean = false,
    val localProcessingEnabled: Boolean = true
)

data class ChatMessage(
    val id: String,
    val sender: ChatSender,
    val text: String,
    val thinkingProcess: String? = null,
    val timestamp: String,
    val modelUsed: String = "Gemini 4 Argon"
)

enum class ChatSender {
    USER, AI, SYSTEM
}

data class FileItemData(
    val name: String,
    val size: String,
    val date: String,
    val type: FileType,
    val path: String
)

enum class FileType {
    DOCUMENT, MEDIA, CODE, ARCHIVE, SYSTEM
}

data class AutomationRule(
    val id: String,
    val title: String,
    val trigger: String,
    val action: String,
    val enabled: Boolean
)
