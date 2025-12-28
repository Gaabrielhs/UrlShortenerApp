package com.example.urlshortenerapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.urlshortenerapp.R
import com.example.urlshortenerapp.ui.preview.LinksListStateProvider
import com.example.urlshortenerapp.ui.theme.UrlShortenerAppTheme

object LinksListScreenTags {
    const val LIST = "LinksListScreen_List"
    const val TITLE = "LinksListScreen_Title"
    const val INPUT = "LinksListScreen_Input"
    const val HISTORY_ITEM = "LinksListScreen_HistoryItem"
    const val DIVIDER = "LinksListScreen_Divider"
}

@Composable
fun LinksListScreen(modifier: Modifier = Modifier, state: LinksListState, actions: LinksListActions) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .testTag(LinksListScreenTags.LIST),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag(LinksListScreenTags.TITLE),
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }

            stickyHeader {
                LinksScreenInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(LinksListScreenTags.INPUT),
                    state = state,
                    actions = actions
                )
            }

            itemsIndexed(state.history, key = { _, item -> item.id }) { index, item ->
                LinkHistoryItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(LinksListScreenTags.HISTORY_ITEM),
                    item
                )

                if (index < state.history.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.testTag(LinksListScreenTags.DIVIDER)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LinksListScreenPreview(@PreviewParameter(LinksListStateProvider::class) state: LinksListState) {
    UrlShortenerAppTheme {
        LinksListScreen(
            state = state,
            actions =
            object : LinksListActions {
                override fun onInputValueChanged(input: String) {}

                override fun onCommit() {}
            }
        )
    }
}
