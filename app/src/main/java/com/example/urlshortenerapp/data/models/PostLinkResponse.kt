package com.example.urlshortenerapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostLinkResponse(val alias: String, @SerialName("_links") val links: Links) {
    @Serializable
    data class Links(val self: String, val short: String)
}
