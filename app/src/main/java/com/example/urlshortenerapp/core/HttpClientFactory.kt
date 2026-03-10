package com.example.urlshortenerapp.core

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
class HttpClientFactory {
    fun create(): HttpClient = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json()
        }
    }
}
