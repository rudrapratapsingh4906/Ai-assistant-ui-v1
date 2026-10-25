package com.example

import com.example.data.HudScreen
import com.example.viewmodel.JarvisViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testJarvisViewModelInitialStateAndNavigation() {
        val viewModel = JarvisViewModel()
        assertEquals(HudScreen.HOME, viewModel.currentScreen.value)
        viewModel.navigateTo(HudScreen.CHAT)
        assertEquals(HudScreen.CHAT, viewModel.currentScreen.value)
    }
}

