package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Token

interface AuthRepository {

    suspend fun getNewToken(
        clientId: String,
        codeVerifier: String,
        code: String,
        redirectUri: String
    ): Token

    suspend fun refreshToken(
        clientId: String,
        refreshToken: String
    ): Token

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun loadRefreshToken(): String?

}