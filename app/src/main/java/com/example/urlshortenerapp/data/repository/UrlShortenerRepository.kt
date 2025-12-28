package com.example.urlshortenerapp.data.repository

import com.example.urlshortenerapp.data.models.PostLinkResponse
import kotlinx.coroutines.flow.StateFlow

interface UrlShortenerRepository {
    suspend fun doShort(url: String): Result<PostLinkResponse>

    fun getHistory(): StateFlow<List<PostLinkResponse>>
}
