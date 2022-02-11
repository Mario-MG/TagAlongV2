package com.hfad.tagalong.network.session

import com.hfad.tagalong.network.session.model.Token
import com.hfad.tagalong.network.session.model.User
import com.hfad.tagalong.network.util.AuthManager
import com.hfad.tagalong.network.util.UserManager
import com.hfad.tagalong.session.SessionData
import com.hfad.tagalong.session.SessionManager
import com.hfad.tagalong.session.SessionManager.SessionState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class SessionManagerImpl : SessionManager(), AuthManager, UserManager {

    private val _sessionState = MutableStateFlow<SessionState>(Loading)

    override var sessionState: SessionState
        get() = _sessionState.value
        set(value) {
            _sessionState.value = value
        }

    override fun logIn(sessionData: SessionData) {
        if (sessionData !is SessionDataImpl) {
            throw IllegalArgumentException()
        }
        sessionState = LoggedInImpl(sessionData)
    }

    fun refreshSession(token: Token) {
        val sessionState = this.sessionState
        if (sessionState !is LoggedInImpl) {
            throw IllegalStateException()
        }
        this.sessionState = LoggedInImpl(
            SessionDataImpl(
                token = token,
                user = sessionState.sessionData.user
            )
        )
    }

    override fun logOut() {
        sessionState = Unlogged
    }

    fun refreshToken(): String {
        val sessionState = this.sessionState
        if (sessionState !is LoggedInImpl) {
            throw IllegalAccessException("Refresh token cannot be accessed while unlogged")
        }
        return sessionState.sessionData.token.refreshToken
    }

    override fun accessToken(): String {
        val sessionState = this.sessionState
        if (sessionState !is LoggedInImpl) {
            throw IllegalAccessException("Access token cannot be accessed while unlogged")
        }
        return sessionState.sessionData.token.accessToken
    }

    override fun user(): User {
        val sessionState = this.sessionState
        if (sessionState !is LoggedInImpl) {
            throw IllegalAccessException("User cannot be accessed while unlogged")
        }
        return sessionState.sessionData.user
    }

    override fun addLoginObserver(
        scope: CoroutineScope,
        onLogIn: () -> Unit
    ) {
        scope.launch {
            _sessionState.collect { state ->
                if (state is LoggedIn) {
                    onLogIn()
                }
            }
        }
    }

    override fun addLogoutObserver(
        scope: CoroutineScope,
        onLogOut: () -> Unit
    ) {
        scope.launch {
            _sessionState.collect { state ->
                if (state is Unlogged) {
                    onLogOut()
                }
            }
        }
    }

    private data class LoggedInImpl(override val sessionData: SessionDataImpl) : LoggedIn(sessionData)

}
