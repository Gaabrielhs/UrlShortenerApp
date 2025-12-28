package com.example.urlshortenerapp.data.service

import com.example.urlshortenerapp.data.models.PostLinkResponse
import com.example.urlshortenerapp.data.models.PostLinkResponseBuilder

class FakeUrlShortenerService : UrlShortenerService {

    var nextResult: Result<PostLinkResponse> = Result.success(
        PostLinkResponseBuilder.Companion.createDefault()
    )

    var postLinkCalled = 0
    var postLinkParameter: String? = null

    override suspend fun postLink(url: String): Result<PostLinkResponse> {
        postLinkCalled++
        postLinkParameter = url
        return nextResult
    }
}
