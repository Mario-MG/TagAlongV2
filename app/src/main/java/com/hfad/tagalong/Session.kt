package com.hfad.tagalong

import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.repository.AuthRepository
import kotlin.properties.Delegates

class Session(
    private val authRepository: AuthRepository,
    private val clientId: String
) {

    private var refreshToken: String? = null
        set(value) {
            isLoggedIn = value != null
            field = value
        }

    private var token: String? = null

    private val loginStatusObservers = mutableListOf<(Boolean) -> Unit>()

    private var isLoggedIn: Boolean by Delegates.observable(false) { _, _, newValue ->
        loginStatusObservers.forEach { it(newValue) }
    }

    fun loginWithToken(token: Token) {
        this.refreshToken = token.refreshToken
        this.token = token.accessToken
    }

    suspend fun getAuthorization(): String {
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

    fun addLoginStatusObserver(observer: (Boolean) -> Unit) {
        loginStatusObservers.add(observer)
    }

}
