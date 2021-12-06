package com.hfad.tagalong

import com.hfad.tagalong.domain.model.User
import com.hfad.tagalong.repository.AuthRepository
import com.hfad.tagalong.repository.UserRepository
import kotlin.properties.Delegates

class Session(
    private val authRepository: AuthRepository,
    private val clientId: String,
    private val userRepository: UserRepository
) {

    private var refreshToken: String? = null

    private var token: String? = null

    private val loginStatusObservers = mutableListOf<(Boolean) -> Unit>()

    var isLoggedIn: Boolean by Delegates.observable(false) { _, _, newValue ->
        loginStatusObservers.forEach { it(newValue) }
    }
        private set

    var user: User? = null

    suspend fun init() {
        authRepository.loadRefreshToken()?.let {
            this.refreshToken = it
        }
    }

    suspend fun login(
        code: String,
        codeVerifier: String,
        redirectUri: String,
        stayLoggedIn: Boolean
    ) {
        val tokenObj = authRepository.getNewToken(
            clientId = clientId,
            code = code,
            codeVerifier = codeVerifier,
            redirectUri = redirectUri
        )
        this.refreshToken = tokenObj.refreshToken
        this.token = tokenObj.accessToken
        if (stayLoggedIn) {
            authRepository.saveRefreshToken(refreshToken!!)
        }
        this.user = userRepository.get(auth = getAuthorizationHeader())
        this.isLoggedIn = true
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

    suspend fun logOut() {
        refreshToken = null
        token = null
        user = null
        authRepository.deleteRefreshToken()
        isLoggedIn = false
    }

}
