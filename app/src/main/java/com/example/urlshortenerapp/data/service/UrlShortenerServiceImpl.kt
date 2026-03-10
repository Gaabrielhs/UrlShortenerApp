package com.example.urlshortenerapp.data.service

import com.example.urlshortenerapp.data.models.PostLinkRequest
import com.example.urlshortenerapp.data.models.PostLinkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UrlShortenerServiceImpl(private val httpClient: HttpClient) :
    UrlShortenerService {
    companion object {
        const val BASE_URL = "https://url-shortener-server.onrender.com/api/alias"
    }

    override suspend fun postLink(url: String): Result<PostLinkResponse> = runCatching {
        httpClient
            .post(BASE_URL) {
                contentType(ContentType.Application.Json)
                setBody(PostLinkRequest(url))
            }.body<PostLinkResponse>()
    }
}
