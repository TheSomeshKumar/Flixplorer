package com.thesomeshkumar.flixplorer.presentation.common

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.thesomeshkumar.flixplorer.R
import org.junit.Rule
import org.junit.Test

class ErrorViewTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showErrorViewTest() {
        val error = rule.activity.getString(R.string.error_unexpected_message)
        rule.setContent {
            ErrorView(errorText = error)
        }

        rule.onNodeWithTag("LottieView")
            .assertIsDisplayed()
        rule.onNodeWithText(error)
            .assertIsDisplayed()
    }
}
