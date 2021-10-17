package com.hfad.tagalong.repository

import com.hfad.tagalong.network.RetrofitAuthService

class AuthRepositoryImpl(
    private val authService: RetrofitAuthService
): AuthRepository {

    override suspend fun getNewToken(
        clientId: String,
        codeVerifier: String,
        code: String
    ): String {
        return authService.getNewToken(
            clientId = clientId,
            codeVerifier = codeVerifier,
            code = code
        ).accessToken
    }

    override suspend fun refreshToken(clientId: String, refreshToken: String): String {
        return authService.refreshToken(
            clientId = clientId,
            refreshToken = refreshToken
        ).accessToken
    }
}