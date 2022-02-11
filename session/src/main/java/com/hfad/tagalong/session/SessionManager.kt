package com.hfad.tagalong.session

import com.hfad.tagalong.session.SessionManager.SessionState.LoggedIn
import kotlinx.coroutines.CoroutineScope

abstract class SessionManager {

    protected abstract var sessionState: SessionState

    val sessionData: SessionData?
        get() {
            val sessionState = sessionState
            return if (sessionState is LoggedIn) sessionState.sessionData else null
        }

    abstract fun logIn(sessionData: SessionData)

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

        abstract class LoggedIn(open val sessionData: SessionData) : SessionState()

        object Unlogged : SessionState()
    }

}