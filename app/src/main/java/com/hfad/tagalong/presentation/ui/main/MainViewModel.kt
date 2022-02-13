package com.hfad.tagalong.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.auth_interactors.LoadSessionData
import com.hfad.tagalong.auth_interactors.RefreshSessionData
import com.hfad.tagalong.auth_interactors.SaveSessionData
import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.auth_interactors_core.session.SessionManager
import com.hfad.tagalong.interactors_core.util.on
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.util.DialogQueue
import com.hfad.tagalong.settings_interactors.LoadStayLoggedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val loadSessionData: LoadSessionData,
    private val refreshSessionData: RefreshSessionData,
    private val loadStayLoggedIn: LoadStayLoggedIn,
    private val saveSessionData: SaveSessionData,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    override val dialogQueue = DialogQueue()

    init {
        sessionManager.addLoginObserver(viewModelScope) {
            isLoggedIn = true
            saveSessionDataIfEnabled(sessionManager.sessionData!!)
        }
        sessionManager.addLogoutObserver(viewModelScope) {
            isLoggedIn = false
        }
        loadSessionData()
    }

    private fun loadSessionData() {
        loadSessionData
            .execute()
            .on(
                success = { sessionData ->
                    sessionData?.let { loadedSessionData ->
                        refreshSessionData(loadedSessionData)
                    } ?: run {
                        sessionManager.logOut()
                    }
                },
                error = {
                    sessionManager.logOut()
                    dialogQueue.appendErrorDialog(BaseApplication.getString(R.string.load_session_error_description))
                }
            )
            .launchIn(viewModelScope)
    }

    private fun refreshSessionData(oldSessionData: SessionData) {
        refreshSessionData
            .execute(oldSessionData = oldSessionData)
            .on(
                success = sessionManager::logIn,
                error = {
                    sessionManager.logOut()
                    dialogQueue.appendErrorDialog(BaseApplication.getString(R.string.load_session_error_description))
                }
            )
            .launchIn(viewModelScope)
    }

    private fun saveSessionDataIfEnabled(sessionData: SessionData) {
        loadStayLoggedIn
            .execute()
            .on(
                success = { stayLoggedIn ->
                    if (stayLoggedIn) {
                        saveSessionData(sessionData)
                    } else {
                        sessionManager.logIn(sessionData)
                    }
                },
                error = {} // TODO
            )
            .launchIn(viewModelScope)
    }

    private fun saveSessionData(sessionData: SessionData) {
        saveSessionData
            .execute(sessionData = sessionData)
            .on(
                success = {
                    sessionManager.logIn(sessionData)
                },
                error = {
                    dialogQueue.appendErrorDialog(BaseApplication.getString(R.string.session_unsaved_error_description))
                }
            )
            .launchIn(viewModelScope)
    }

    fun addLoginSuccessObserver(owner: LifecycleOwner, onLoginSuccess: () -> Unit) {
        sessionManager.addLoginObserver(owner.lifecycleScope, onLoginSuccess)
    }

    fun addLogoutObserver(owner: LifecycleOwner, onLogOut: () -> Unit) {
        sessionManager.addLogoutObserver(owner.lifecycleScope, onLogOut)
    }

}