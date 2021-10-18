package com.hfad.tagalong

import com.hfad.tagalong.repository.AuthRepository

class Session(
    private val authRepository: AuthRepository,
    private val refreshToken: String? = null,
    private val clientId: String
) {

    private var token: String? = null

    suspend fun getToken(): String {
        if (token == null) {
            this.refreshToken()
        }
        return "Bearer $token" // TODO: Handle unsuccessful refresh
    }

    suspend fun refreshToken() {
        refreshToken?.let { refreshToken ->
            token = authRepository.refreshToken(
                clientId = clientId,
                refreshToken = refreshToken
            )
        }
    }

}
