package com.hfad.tagalong.presentation.session

sealed class SessionState(open val session: Session?) {

    object Loading : SessionState(null)

    data class LoggedIn(override val session: Session) : SessionState(session)

    object Unlogged : SessionState(null)

}
