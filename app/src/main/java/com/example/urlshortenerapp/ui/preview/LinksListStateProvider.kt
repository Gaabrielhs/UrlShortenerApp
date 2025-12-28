package com.example.urlshortenerapp.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.urlshortenerapp.ui.LinksListState

class LinksListStateProvider : PreviewParameterProvider<LinksListState> {
    private val historyMock =
        List(100) {
            LinksListState.LinkHistory(
                id = "id $it",
                full = "full: $it",
                short = "short: $it"
            )
        }

    override val values =
        sequenceOf(
            LinksListState(
                inputValue = "Some input",
                history = historyMock,
                status = LinksListState.Status.Done
            ),
            LinksListState(
                history = historyMock,
                status = LinksListState.Status.Loading
            ),
            LinksListState(
                history = historyMock,
                status = LinksListState.Status.Error("Error message")
            )
        )
}
