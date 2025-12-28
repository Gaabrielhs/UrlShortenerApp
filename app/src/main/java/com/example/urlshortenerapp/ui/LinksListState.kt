package com.example.urlshortenerapp.ui

import androidx.compose.runtime.Immutable
import com.example.urlshortenerapp.data.models.PostLinkResponse

@Immutable
data class LinksListState(
    val inputValue: String = "",
    val history: List<LinkHistory> = emptyList(),
    val status: Status = Status.Done
) {
    sealed interface Status {
        data object Done : Status

        data object Loading : Status

        data class Error(val message: String) : Status

        val isError: Boolean
            get() = this is Error

        val isLoading: Boolean
            get() = this is Loading

        val error: Error?
            get() = this as? Error
    }

    @Immutable
    data class LinkHistory(val id: String, val full: String, val short: String) {
        companion object {
            fun from(postLinkResponse: List<PostLinkResponse>) = postLinkResponse.map {
                LinkHistory(
                    id = it.alias,
                    full = it.links.self,
                    short = it.links.short
                )
            }
        }
    }
}
