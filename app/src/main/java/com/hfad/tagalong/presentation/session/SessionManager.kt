package com.hfad.tagalong.presentation.session

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.domain.model.User
import com.hfad.tagalong.repository.AuthRepository
import com.hfad.tagalong.repository.UserRepository

class SessionManager(
    private val authRepository: AuthRepository,
    private val clientId: String,
    private val userRepository: UserRepository
) {
    private val sessionState = MutableLiveData<SessionState>(SessionState.Loading)

    suspend fun init() {
        authRepository.loadRefreshToken()?.let { savedRefreshToken ->
            refreshToken(refreshToken = savedRefreshToken)
        } ?: sessionState.postValue(SessionState.Unlogged)
    }

    suspend fun refreshToken() {
        val sessionState = this.sessionState.value
        if (sessionState is SessionState.LoggedIn) {
            sessionState.session.token.refreshToken?.let { refreshToken ->
                refreshToken(refreshToken = refreshToken)
            }
        }
    }

    private suspend fun refreshToken(refreshToken: String) {
        val token = authRepository.refreshToken(
            clientId = clientId,
            refreshToken = refreshToken
        )
        token.refreshToken?.let { newRefreshToken ->
            authRepository.saveRefreshToken(newRefreshToken)
        }
        this.sessionState.postValue(SessionState.LoggedIn(Session(
            token = token,
            user = sessionState.value?.session?.user
                ?: userRepository.get(auth = getAuthorizationHeader(token))
        )))
    }

    suspend fun login(
        code: String,
        codeVerifier: String,
        redirectUri: String,
        stayLoggedIn: Boolean
    ) {
        val token = authRepository.getNewToken(
            clientId = clientId,
            code = code,
            codeVerifier = codeVerifier,
            redirectUri = redirectUri
        )
        val user = userRepository.get(auth = getAuthorizationHeader(token))
        login(token = token, user = user, stayLoggedIn = stayLoggedIn)
    }

    private suspend fun login(token: Token, user: User, stayLoggedIn: Boolean) {
        if (stayLoggedIn && token.refreshToken != null) {
            authRepository.saveRefreshToken(token.refreshToken)
        }
        sessionState.postValue(SessionState.LoggedIn(Session(token = token, user = user)))
    }

    fun getAuthorizationHeader(): String {
        val sessionState = this.sessionState.value
        if (sessionState !is SessionState.LoggedIn) {
            throw IllegalAccessException("Token cannot be accessed while unlogged")
        }
        return getAuthorizationHeader(sessionState.session.token)
    }

    private fun getAuthorizationHeader(token: Token) = "Bearer ${token.accessToken}"

    fun getUser(): User {
        val sessionState = this.sessionState.value
        if (sessionState !is SessionState.LoggedIn) {
            throw IllegalAccessException("User cannot be accessed while unlogged")
        }
        return sessionState.session.user
    }

    suspend fun logOut() {
        authRepository.deleteRefreshToken()
        sessionState.postValue(SessionState.Unlogged)
    }

    fun addLoginSuccessObserver(owner: LifecycleOwner, onLoginSuccess: () -> Unit) {
        sessionState.observe(owner, { sessionState ->
            if (sessionState is SessionState.LoggedIn) {
                onLoginSuccess()
            }
        })
    }

    fun addLoginSuccessObserver(onLoginSuccess: () -> Unit) {
        sessionState.observeForever { sessionState ->
            if (sessionState is SessionState.LoggedIn) {
                onLoginSuccess()
            }
        }
    }

    fun addLogoutObserver(owner: LifecycleOwner, onLogOut: () -> Unit) {
        sessionState.observe(owner, { sessionState ->
            if (sessionState is SessionState.Unlogged) {
                onLogOut()
            }
        })
    }

    fun addLogoutObserver(onLogOut: () -> Unit) {
        sessionState.observeForever { sessionState ->
            if (sessionState is SessionState.Unlogged) {
                onLogOut()
            }
        }
    }

}
