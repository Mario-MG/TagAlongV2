package com.hfad.tagalong.domain.model

data class Token(
    val accessToken: String,
    val refreshToken: String? = null // TODO: Make this non-nullable
)