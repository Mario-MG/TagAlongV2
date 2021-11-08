package com.hfad.tagalong

import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.repository.AuthRepository

class Session(
    private val authRepository: AuthRepository,
    private val clientId: String
) {

    private var refreshToken: String? = null

    private var token: String? = null

    fun loginWithToken(token: Token) {
        this.refreshToken = token.refreshToken
        this.token = token.accessToken
    }

    fun isLoggedIn(): Boolean = refreshToken != null

    suspend fun getToken(): String {
        if (token == null) {
            this.refreshToken()
        }
        return "Bearer $token" // TODO: Handle unsuccessful refresh
    }

    suspend fun refreshToken() {
        refreshToken?.let { oldRefreshToken ->
            val tokenObj = authRepository.refreshToken(
                clientId = clientId,
                refreshToken = oldRefreshToken
            )
            token = tokenObj.accessToken
            tokenObj.refreshToken?.let { newRefreshToken ->
                this.refreshToken = newRefreshToken
            }
        }
    }

}
