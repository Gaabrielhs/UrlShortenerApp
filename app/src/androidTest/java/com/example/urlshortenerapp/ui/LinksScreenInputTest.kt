package com.example.urlshortenerapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class LinksScreenInputTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialState() {
        val inputText = "input text"
        val state = createState(input = inputText, LinksListState.Status.Done)
        composeTestRule.setContent {
            LinksScreenInput(
                state = state,
                actions = createActions()
            )
        }

        composeTestRule.onNodeWithTag(
            LinkScreenInputTags.TEXT_FIELD
        ).assertIsDisplayed().assertTextEquals(inputText)
        composeTestRule.onNodeWithTag(LinkScreenInputTags.BUTTON).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LinkScreenInputTags.LOADING_INDICATOR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LinkScreenInputTags.ERROR_MESSAGE).assertDoesNotExist()
    }

    @Test
    fun loadingState() {
        val state = createState(status = LinksListState.Status.Loading)
        composeTestRule.setContent {
            LinksScreenInput(
                state = state,
                actions = createActions()
            )
        }

        composeTestRule.onNodeWithTag(LinkScreenInputTags.TEXT_FIELD).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LinkScreenInputTags.BUTTON).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LinkScreenInputTags.LOADING_INDICATOR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LinkScreenInputTags.ERROR_MESSAGE).assertDoesNotExist()
    }

    @Test
    fun errorState() {
        val errorMessage = "error message"
        val state = createState(status = LinksListState.Status.Error(errorMessage))
        composeTestRule.setContent {
            LinksScreenInput(
                state = state,
                actions = createActions()
            )
        }

        composeTestRule.onNodeWithTag(LinkScreenInputTags.TEXT_FIELD).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LinkScreenInputTags.BUTTON).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LinkScreenInputTags.LOADING_INDICATOR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            LinkScreenInputTags.ERROR_MESSAGE,
            useUnmergedTree = true
        ).assertIsDisplayed().assertTextEquals(errorMessage)
    }

    private fun createState(input: String = "", status: LinksListState.Status = LinksListState.Status.Done) =
        LinksListState(inputValue = input, status = status)

    private fun createActions() = object : LinksListActions {
        override fun onInputValueChanged(input: String) {}
        override fun onCommit() {}
    }
}
