package com.hfad.tagalong.network.session

import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.auth_interactors_core.session.SessionManager
import com.hfad.tagalong.auth_interactors_core.session.SessionManager.SessionState.*
import com.hfad.tagalong.network.session.model.User
import com.hfad.tagalong.network.util.AuthManager
import com.hfad.tagalong.network.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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

    override fun refresh(sessionData: SessionData) {
        val sessionState = sessionState
        if (sessionState !is LoggedInImpl) {
            throw IllegalAccessException("Session data can only be refreshed if session state is already LoggedIn")
        }
        if (sessionData !is SessionDataImpl) {
            throw IllegalArgumentException()
        }
        sessionState.sessionDataImpl = sessionData
    }

    override fun logOut() {
        sessionState = Unlogged
    }

    override fun accessToken(): String {
        val sessionState = this.sessionState
        if (sessionState !is LoggedInImpl) {
            throw IllegalAccessException("Access token cannot be accessed while unlogged")
        }
        return sessionState.sessionDataImpl.token.accessToken
    }

    override fun user(): User {
        val sessionState = this.sessionState
        if (sessionState !is LoggedInImpl) {
            throw IllegalAccessException("User cannot be accessed while unlogged")
        }
        return sessionState.sessionDataImpl.user
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

    private data class LoggedInImpl(var sessionDataImpl: SessionDataImpl) : LoggedIn(sessionDataImpl)

}
