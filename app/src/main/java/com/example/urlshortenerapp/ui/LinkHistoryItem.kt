package com.example.urlshortenerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.example.urlshortenerapp.R
import com.example.urlshortenerapp.ui.theme.UrlShortenerAppTheme

@Composable
fun LinkHistoryItem(modifier: Modifier = Modifier.Companion, linkHistory: LinksListState.LinkHistory) {
    Column(
        modifier = modifier
    ) {
        ItemSection(
            label = stringResource(R.string.full_url_label),
            link = linkHistory.full
        )

        ItemSection(
            label = stringResource(R.string.shortened_url_label),
            link = linkHistory.short
        )
    }
}

@Composable
fun ItemSection(label: String, link: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall
    )
    Text(
        text = link,
        style = MaterialTheme.typography.bodyMedium,
        textDecoration = TextDecoration.Underline,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview
@Composable
fun LinkHistoryItemPreview() {
    UrlShortenerAppTheme {
        LinkHistoryItem(
            linkHistory =
            LinksListState.LinkHistory(
                id = "id",
                full = "full",
                short = "short"
            )
        )
    }
}
