package com.hfad.tagalong

import com.hfad.tagalong.repository.AuthRepository

class Session(
    private val authRepository: AuthRepository,
    private val refreshToken: String? = null,
    private val clientId: String
) {

    var token: String? = null
        private set

    suspend fun refreshToken() {
        refreshToken?.let { refreshToken ->
            token = authRepository.refreshToken(
                clientId = clientId,
                refreshToken = refreshToken
            )
        }
    }

}
