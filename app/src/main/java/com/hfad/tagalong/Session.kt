package com.hfad.tagalong

import com.hfad.tagalong.repository.AuthRepository
import kotlin.properties.Delegates

class Session(
    private val authRepository: AuthRepository,
    private val clientId: String
) {

    private var refreshToken: String? = null
        set(value) {
            field = value
            isLoggedIn = value != null
        }

    private var token: String? = null

    private val loginStatusObservers = mutableListOf<(Boolean) -> Unit>()

    var isLoggedIn: Boolean by Delegates.observable(false) { _, _, newValue ->
        loginStatusObservers.forEach { it(newValue) }
    }
        private set

    suspend fun init() {
        authRepository.loadRefreshToken()?.let {
            this.refreshToken = it
        }
    }

    suspend fun login(
        code: String,
        codeVerifier: String,
        redirectUri: String
    ) {
        val tokenObj = authRepository.getNewToken(
            clientId = clientId,
            code = code,
            codeVerifier = codeVerifier,
            redirectUri = redirectUri
        )
        this.refreshToken = tokenObj.refreshToken
        this.token = tokenObj.accessToken
        authRepository.saveRefreshToken(refreshToken!!)
    }

    suspend fun getAuthorizationHeader(): String {
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
                authRepository.saveRefreshToken(newRefreshToken)
            }
        }
    }

    fun addLoginStatusObserver(observer: (Boolean) -> Unit) {
        loginStatusObservers.add(observer)
    }

}
