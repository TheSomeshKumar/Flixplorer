package com.thesomeshkumar.flixplorer.ui.screens.settings

import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import com.thesomeshkumar.flixplorer.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK(relaxed = true)
    private lateinit var repository: FlixplorerRepository

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        viewModel = SettingsViewModel(repository)
    }

    @Test
    fun `updateUseM3() should update the flixRepository`() {
        runTest {
            val expectedUseM3 = true
            coEvery { repository.updateUseMaterial3(expectedUseM3) } just Runs

            viewModel.updateUseM3(expectedUseM3)

            coVerify { repository.updateUseMaterial3(expectedUseM3) }
        }
    }

    @Test
    fun `updateUseDarkMode() should update the flixRepository`() {
        runTest {
            val expectedUseDarkMode = "light"
            coEvery { repository.updateUseDarkMode(expectedUseDarkMode) } just Runs

            viewModel.updateUseDarkMode(expectedUseDarkMode)

            coVerify { repository.updateUseDarkMode(expectedUseDarkMode) }
        }
    }
}
