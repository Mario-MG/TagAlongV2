package com.hfad.tagalong.presentation.session

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.domain.model.User
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.login.SaveSessionInfo
import com.hfad.tagalong.network.util.AuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

class SessionManager(
    private val saveSessionInfo: SaveSessionInfo
) : AuthManager {

    private val sessionState = MutableLiveData<SessionState>(SessionState.Loading)

    val user: User
        get() {
            sessionState.value?.let { sessionState ->
                return sessionState.user
                    ?: throw IllegalAccessException("User cannot be accessed while unlogged")
            } ?: throw IllegalStateException("SessionState is null")
        }

    val refreshToken: String
        get() {
            sessionState.value?.let { sessionState ->
                return sessionState.token?.refreshToken
                    ?: throw IllegalAccessException("Refresh token cannot be accessed while unlogged")
            } ?: throw IllegalStateException("SessionState is null")
        }

    fun login(token: Token, user: User) {
        sessionState.postValue(SessionState.LoggedIn(token = token, user = user))
    }

    suspend fun refreshToken(token: Token) {
        withContext(Dispatchers.Main) {
            sessionState.value = SessionState.LoggedIn(token = token, user = user)
            saveSessionInfo
                .execute(token = token)
                .on(
                    error = { /* TODO */ }
                )
                .launchIn(this)
        }
    }

    fun logOut() {
        sessionState.postValue(SessionState.Unlogged)
    }

    fun getAuthorizationHeader(): String {
        val sessionState = this.sessionState.value
        if (sessionState !is SessionState.LoggedIn) {
            throw IllegalAccessException("Token cannot be accessed while unlogged")
        }
        return "Bearer ${sessionState.token.accessToken}"
    }

    override fun accessToken(): String {
        val sessionState = this.sessionState.value
        if (sessionState !is SessionState.LoggedIn) {
            throw IllegalAccessException("Token cannot be accessed while unlogged")
        }
        return sessionState.token.accessToken
    }

    fun addLoginObserver(owner: LifecycleOwner, onLogIn: () -> Unit) {
        sessionState.observe(owner, { sessionState ->
            if (sessionState is SessionState.LoggedIn) {
                onLogIn()
            }
        })
    }

    fun addLoginObserver(onLogIn: () -> Unit) {
        sessionState.observeForever { sessionState ->
            if (sessionState is SessionState.LoggedIn) {
                onLogIn()
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

    private sealed class SessionState(
        open val token: Token? = null,
        open val user: User? = null
    ) {

        object Loading : SessionState()

        data class LoggedIn(
            override val token: Token,
            override val user: User
        ) : SessionState(token = token, user = user)

        object Unlogged : SessionState()

    }

}
