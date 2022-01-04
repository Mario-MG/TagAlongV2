package com.hfad.tagalong.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.di.APP_CLIENT_ID
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.login.GetTokenFromRefreshToken
import com.hfad.tagalong.interactors.login.LoadSessionInfo
import com.hfad.tagalong.interactors.login.LoadUser
import com.hfad.tagalong.interactors.login.SaveSessionInfo
import com.hfad.tagalong.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val loadSessionInfo: LoadSessionInfo,
    private val getTokenFromRefreshToken: GetTokenFromRefreshToken,
    private val loadUser: LoadUser,
    private val saveSessionInfo: SaveSessionInfo,
    private val sessionManager: SessionManager,
    @Named(APP_CLIENT_ID) private val clientId: String
) : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    init {
        sessionManager.addLoginObserver {
            isLoggedIn = true
        }
        sessionManager.addLogoutObserver {
            isLoggedIn = false
        }
        loadSessionInfo()
    }

    private fun loadSessionInfo() {
        loadSessionInfo
            .execute()
            .on(
                success = { refreshToken ->
                    if (refreshToken != null) {
                        getTokenFromRefreshToken(refreshToken)
                    } else {
                        sessionManager.logOut()
                    }
                },
                error = { /* TODO */ }
            )
            .launchIn(viewModelScope)
    }

    private fun getTokenFromRefreshToken(refreshToken: String) {
        getTokenFromRefreshToken
            .execute(
                clientId = clientId,
                refreshToken = refreshToken
            )
            .on(
                success = { token ->
                    loadUser(token)
                    saveSessionInfo(token)
                },
                error = { sessionManager.logOut() } // TODO
            )
            .launchIn(viewModelScope)
    }

    private fun loadUser(token: Token) {
        loadUser
            .execute(token = token)
            .on(
                success = { user ->
                    sessionManager.login(
                        token = token,
                        user = user
                    )
                },
                error  = { sessionManager.logOut() } // TODO
            )
            .launchIn(viewModelScope)
    }

    private fun saveSessionInfo(token: Token) {
        saveSessionInfo
            .execute(token = token)
            .on(
                error = { /* TODO */ }
            )
            .launchIn(viewModelScope)
    }

    fun addLoginSuccessObserver(owner: LifecycleOwner, onLoginSuccess: () -> Unit) {
        sessionManager.addLoginObserver(owner, onLoginSuccess)
    }

    fun addLogoutObserver(owner: LifecycleOwner, onLogOut: () -> Unit) {
        sessionManager.addLogoutObserver(owner, onLogOut)
    }

}