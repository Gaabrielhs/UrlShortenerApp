package com.example.urlshortenerapp.data.repository

import com.example.urlshortenerapp.data.models.PostLinkResponse
import com.example.urlshortenerapp.data.service.UrlShortenerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UrlShortenerRepositoryImpl(private val service: UrlShortenerService) : UrlShortenerRepository {
    // Saved in memory for now, but can be easily migrated to Room database or Datastore
    private val history: MutableStateFlow<List<PostLinkResponse>> = MutableStateFlow(emptyList())

    override suspend fun doShort(url: String): Result<PostLinkResponse> {
        val result = service.postLink(url)
        result.getOrNull()?.let { response ->
            history.update { listOf(response) + it }
        }

        return result
    }

    override fun getHistory() = history.asStateFlow()
}

private val mockList =
    List(100) {
        PostLinkResponse(
            alias = "id: $it",
            links =
            PostLinkResponse.Links(
                self = "old $it",
                short = "new $it"
            )
        )
    }
