package com.hfad.tagalong.auth_interactors_core.session

import com.hfad.tagalong.auth_interactors_core.session.SessionManager.SessionState.LoggedIn
import kotlinx.coroutines.CoroutineScope

abstract class SessionManager {

    protected abstract var sessionState: SessionState

    val sessionData: SessionData?
        get() {
            val sessionState = sessionState
            return if (sessionState is LoggedIn) sessionState.sessionData else null
        }

    abstract fun logIn(sessionData: SessionData)

    abstract fun refresh(sessionData: SessionData)

    abstract fun logOut()

    abstract fun addLoginObserver(
        scope: CoroutineScope,
        onLogIn: () -> Unit
    )

    abstract fun addLogoutObserver(
        scope: CoroutineScope,
        onLogOut: () -> Unit
    )

    protected sealed class SessionState {
        object Loading : SessionState()

        abstract class LoggedIn(open var sessionData: SessionData) : SessionState()

        object Unlogged : SessionState()
    }

}