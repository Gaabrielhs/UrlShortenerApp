package com.example.urlshortenerapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PostLinkRequest(val url: String)
