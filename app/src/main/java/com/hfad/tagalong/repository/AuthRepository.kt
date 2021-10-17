package com.hfad.tagalong.repository

interface AuthRepository {

    suspend fun getNewToken(
        clientId: String,
        codeVerifier: String,
        code: String
    ): String

    suspend fun refreshToken(
        clientId: String,
        refreshToken: String
    ): String

}