package com.example.urlshortenerapp.data.repository

import com.example.urlshortenerapp.data.models.PostLinkResponse
import com.example.urlshortenerapp.data.models.PostLinkResponseBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeUrlShortenerRepository : UrlShortenerRepository {
    var nextResponse: Result<PostLinkResponse> = Result.success(
        PostLinkResponseBuilder.createDefault()
    )
    var doShortCalled: Int = 0
    var doShortParameter: String? = null

    override suspend fun doShort(url: String): Result<PostLinkResponse> {
        doShortCalled++
        doShortParameter = url
        return nextResponse
    }

    var history = MutableStateFlow(emptyList<PostLinkResponse>())
    override fun getHistory(): StateFlow<List<PostLinkResponse>> = history
}
