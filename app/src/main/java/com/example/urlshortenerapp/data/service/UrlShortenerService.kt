package com.example.urlshortenerapp.data.service

import com.example.urlshortenerapp.data.models.PostLinkResponse

interface UrlShortenerService {
    suspend fun postLink(url: String): Result<PostLinkResponse>
}
