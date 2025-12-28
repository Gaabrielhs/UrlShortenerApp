package com.example.urlshortenerapp.data.models

class PostLinkResponseBuilder {
    companion object {
        fun createDefault() = PostLinkResponse(
            alias = "alias",
            links = PostLinkResponse.Links(
                self = "self-link",
                short = "short-link"
            )
        )
    }
}
