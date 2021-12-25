package com.hfad.tagalong.presentation.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.di.APP_CLIENT_ID
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.interactors.login.GetTokenFromRefreshToken
import com.hfad.tagalong.interactors.login.LoadSessionInfo
import com.hfad.tagalong.interactors.login.LoadUser
import com.hfad.tagalong.interactors.login.SaveSessionInfo
import com.hfad.tagalong.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    val isLoggedIn = mutableStateOf(false)

    init {
        sessionManager.addLoginObserver {
            isLoggedIn.value = true
        }
        sessionManager.addLogoutObserver {
            isLoggedIn.value = false
        }
        loadSessionInfo()
    }

    private fun loadSessionInfo() {
        loadSessionInfo
            .execute()
            .onEach { dataState ->
                dataState.data?.let { refreshToken ->
                    if (refreshToken.isNotBlank()) {
                        getTokenFromRefreshToken(refreshToken)
                    } else {
                        sessionManager.logOut()
                    }
                }

                dataState.error?.let { error ->
                    // TODO
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getTokenFromRefreshToken(refreshToken: String) {
        getTokenFromRefreshToken
            .execute(
                clientId = clientId,
                refreshToken = refreshToken
            )
            .onEach { dataState ->
                dataState.data?.let { token ->
                    loadUser(token)
                    saveSessionInfo(token)
                }

                dataState.error?.let { error ->
                    // TODO
                    sessionManager.logOut()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadUser(token: Token) {
        loadUser
            .execute(token = token)
            .onEach { dataState ->
                dataState.data?.let { user ->
                    sessionManager.login(
                        token = token,
                        user = user
                    )
                }

                dataState.error?.let { error ->
                    // TODO
                    sessionManager.logOut()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun saveSessionInfo(token: Token) {
        saveSessionInfo
            .execute(token = token)
            .onEach { dataState ->
                dataState.error?.let { error ->
                    //  TODO
                }
            }
            .launchIn(viewModelScope)
    }

    fun addLoginSuccessObserver(owner: LifecycleOwner, onLoginSuccess: () -> Unit) {
        sessionManager.addLoginObserver(owner, {
            onLoginSuccess()
        })
    }

    fun addLogoutObserver(owner: LifecycleOwner, onLogOut: () -> Unit) {
        sessionManager.addLogoutObserver(owner, {
            onLogOut()
        })
    }

}