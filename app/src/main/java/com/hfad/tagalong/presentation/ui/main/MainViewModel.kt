package com.hfad.tagalong.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.auth_interactors.LoadSessionData
import com.hfad.tagalong.auth_interactors.LogOut
import com.hfad.tagalong.auth_interactors.RefreshSession
import com.hfad.tagalong.auth_interactors.SaveSessionData
import com.hfad.tagalong.interactors_core.util.on
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.util.DialogQueue
import com.hfad.tagalong.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val loadSessionData: LoadSessionData,
    private val refreshSession: RefreshSession,
    private val saveSessionData: SaveSessionData,
    private val logOut: LogOut,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    override val dialogQueue = DialogQueue()

    init {
        sessionManager.addLoginObserver(viewModelScope) {
            if (!isLoggedIn) refreshSession() // TODO: Refine load/refresh/save flow
            isLoggedIn = true
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
                error = {
                    logOut()
                    dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.load_session_error_description))
                }
            )
            .launchIn(viewModelScope)
    }

    private fun refreshSession() {
        refreshSession
            .execute()
            .on(
                success = {
                    saveSessionData()
                },
                error = { error ->
                    logOut()
                    appendGenericErrorToQueue(error)
                }
            )
            .launchIn(viewModelScope)
    }

    private fun saveSessionData() {
        saveSessionData
            .execute()
            .on(
                error = {
                    dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.session_unsaved_error_description))
                }
            )
            .launchIn(viewModelScope)
    }

    private fun logOut() {
        logOut
            .execute()
            .on(
                error = {} // TODO
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