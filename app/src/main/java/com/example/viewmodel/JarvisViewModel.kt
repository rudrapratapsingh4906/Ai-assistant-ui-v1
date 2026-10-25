package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AiRepository
import com.example.data.AutomationRule
import com.example.data.ChatMessage
import com.example.data.ChatSender
import com.example.data.ConversationItem
import com.example.data.FileItemData
import com.example.data.FileType
import com.example.data.HudScreen
import com.example.data.SystemStatusData
import com.example.data.TaskItem
import com.example.data.TaskState
import com.example.data.VoiceAiSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class JarvisViewModel(
    private val aiRepository: AiRepository = AiRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(HudScreen.HOME)
    val currentScreen: StateFlow<HudScreen> = _currentScreen.asStateFlow()

    private val _systemStatus = MutableStateFlow(SystemStatusData())
    val systemStatus: StateFlow<SystemStatusData> = _systemStatus.asStateFlow()

    private val _voiceAiSettings = MutableStateFlow(VoiceAiSettings())
    val voiceAiSettings: StateFlow<VoiceAiSettings> = _voiceAiSettings.asStateFlow()

    private val _useHighThinkingMode = MutableStateFlow(true)
    val useHighThinkingMode: StateFlow<Boolean> = _useHighThinkingMode.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _tasks = MutableStateFlow(
        listOf(
            TaskItem("t1", "Analyzing system", "Running...", 0.38f, TaskState.RUNNING),
            TaskItem("t2", "File indexing", "Waiting...", 0.0f, TaskState.WAITING),
            TaskItem("t3", "Background sync", "Completed", 1.0f, TaskState.COMPLETED),
            TaskItem("t4", "Security scan", "Pending", 0.0f, TaskState.PENDING)
        )
    )
    val tasks: StateFlow<List<TaskItem>> = _tasks.asStateFlow()

    private val _recentConversations = MutableStateFlow(
        listOf(
            ConversationItem("c1", "Project Planning", "Let's plan the next phase...", "10:12", "Planning"),
            ConversationItem("c2", "AI Image Generation", "Created 3 images", "09:45", "Creative"),
            ConversationItem("c3", "System Backup", "Completed successfully", "Yesterday", "Maintenance"),
            ConversationItem("c4", "Quick Note", "Here's your note...", "Yesterday", "Notes")
        )
    )
    val recentConversations: StateFlow<List<ConversationItem>> = _recentConversations.asStateFlow()

    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = "m0",
                sender = ChatSender.AI,
                text = "Hello! I am your JARVIS AI Assistant initialized with Gemini 4 Argon High Thinking Mode. How may I assist your operations today?",
                thinkingProcess = "System booted. High thinking protocols active.",
                timestamp = "10:20"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _automationRules = MutableStateFlow(
        listOf(
            AutomationRule("a1", "Battery Saver Directive", "Battery level < 20%", "Enable Eco Mode & throttle background tasks", true),
            AutomationRule("a2", "Nightly Security Audit", "Every day at 02:00 AM", "Run full deep scan & update offline threat DB", true),
            AutomationRule("a3", "Auto Storage Cleanup", "Storage usage > 85%", "Purge temporary cache files & obsolete logs", false)
        )
    )
    val automationRules: StateFlow<List<AutomationRule>> = _automationRules.asStateFlow()

    private val _files = MutableStateFlow(
        listOf(
            FileItemData("jarvis_core_dump.bin", "1.2 GB", "Today, 10:00", FileType.SYSTEM, "/sys/core/"),
            FileItemData("system_architecture_diagram.png", "4.8 MB", "Yesterday", FileType.MEDIA, "/docs/images/"),
            FileItemData("jarvis_subroutine_opt.kt", "128 KB", "2 days ago", FileType.CODE, "/src/ai/"),
            FileItemData("encrypted_vault_keys.dat", "16 KB", "3 days ago", FileType.ARCHIVE, "/sec/keys/")
        )
    )
    val files: StateFlow<List<FileItemData>> = _files.asStateFlow()

    fun navigateTo(screen: HudScreen) {
        _currentScreen.value = screen
    }

    fun toggleHighThinkingMode(enabled: Boolean) {
        _useHighThinkingMode.value = enabled
        _systemStatus.value = _systemStatus.value.copy(
            modelName = if (enabled) "Gemini 4 Argon" else "Gemini 3.8 Flash",
            thinkingLevel = if (enabled) "HIGH" else "STANDARD"
        )
    }

    fun toggleTaskState(taskId: String) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) {
                val newState = when (task.state) {
                    TaskState.RUNNING -> TaskState.WAITING
                    TaskState.WAITING -> TaskState.RUNNING
                    TaskState.PENDING -> TaskState.RUNNING
                    TaskState.COMPLETED -> TaskState.COMPLETED
                }
                val newStatusText = when (newState) {
                    TaskState.RUNNING -> "Running..."
                    TaskState.WAITING -> "Paused"
                    TaskState.COMPLETED -> "Completed"
                    TaskState.PENDING -> "Pending"
                }
                task.copy(state = newState, statusText = newStatusText)
            } else task
        }
    }

    fun toggleWakeWord(enabled: Boolean) {
        _voiceAiSettings.value = _voiceAiSettings.value.copy(wakeWordEnabled = enabled)
    }

    fun toggleVoiceResponse(enabled: Boolean) {
        _voiceAiSettings.value = _voiceAiSettings.value.copy(voiceResponseEnabled = enabled)
    }

    fun toggleOfflineMode(enabled: Boolean) {
        _voiceAiSettings.value = _voiceAiSettings.value.copy(offlineModeEnabled = enabled)
    }

    fun toggleLocalProcessing(enabled: Boolean) {
        _voiceAiSettings.value = _voiceAiSettings.value.copy(localProcessingEnabled = enabled)
    }

    fun toggleAutomation(ruleId: String) {
        _automationRules.value = _automationRules.value.map {
            if (it.id == ruleId) it.copy(enabled = !it.enabled) else it
        }
    }

    fun sendChatMessage(userText: String) {
        if (userText.isBlank()) return

        val time = aiRepository.currentTimeFormatted()
        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = ChatSender.USER,
            text = userText,
            timestamp = time
        )

        _chatMessages.value = _chatMessages.value + userMsg
        _isGenerating.value = true

        val aiMsgId = UUID.randomUUID().toString()
        val highThinking = _useHighThinkingMode.value
        val modelLabel = if (highThinking) "Gemini 4 Argon" else "Gemini 3.8 Flash"

        val initialAiMsg = ChatMessage(
            id = aiMsgId,
            sender = ChatSender.AI,
            text = "",
            thinkingProcess = if (highThinking) "Initializing High Thinking reasoning engine..." else null,
            timestamp = time,
            modelUsed = modelLabel
        )

        _chatMessages.value = _chatMessages.value + initialAiMsg

        viewModelScope.launch {
            aiRepository.streamAiResponse(userText, highThinking).collect { (thinkingMsg, streamText) ->
                _chatMessages.value = _chatMessages.value.map { msg ->
                    if (msg.id == aiMsgId) {
                        msg.copy(
                            text = streamText,
                            thinkingProcess = thinkingMsg
                        )
                    } else msg
                }
            }
            _isGenerating.value = false
        }
    }
}
