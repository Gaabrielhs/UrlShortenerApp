package com.example.urlshortenerapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test

class LinksListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialDefaultState() {
        val state = createState()
        composeTestRule.setContent {
            LinksListScreen(
                state = state,
                actions = createActions()
            )
        }

        composeTestRule.onNodeWithTag(LinksListScreenTags.TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LinksListScreenTags.INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LinksListScreenTags.LIST).assertIsDisplayed()
    }

    @Test
    fun stickyHeaderBehavior() {
        val state = createState(historyMock)
        composeTestRule.setContent {
            LinksListScreen(
                state = state,
                actions = createActions()
            )
        }

        composeTestRule.onAllNodesWithTag(
            LinksListScreenTags.HISTORY_ITEM
        ).onLast().performScrollTo()
        composeTestRule.onNodeWithTag(LinksListScreenTags.TITLE).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(LinksListScreenTags.INPUT).assertIsDisplayed()
    }

    private fun createState(history: List<LinksListState.LinkHistory> = emptyList()) = LinksListState(history = history)

    private val historyMock =
        List(100) {
            LinksListState.LinkHistory(
                id = "id $it",
                full = "full: $it",
                short = "short: $it"
            )
        }
    private fun createActions() = object : LinksListActions {
        override fun onInputValueChanged(input: String) {}
        override fun onCommit() {}
    }
}
