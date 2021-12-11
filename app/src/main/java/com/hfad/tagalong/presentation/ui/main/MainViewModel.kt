package com.hfad.tagalong.presentation.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor (
    private val sessionManager: SessionManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            initSession()
        }
    }

    private suspend fun initSession() {
        sessionManager.init()
    }

    fun addLoginSuccessObserver(owner: LifecycleOwner, onLoginSuccess: () -> Unit) {
        sessionManager.addLoginSuccessObserver(owner, {
            onLoginSuccess()
        })
    }

    fun addLogoutObserver(owner: LifecycleOwner, onLogOut: () -> Unit) {
        sessionManager.addLogoutObserver(owner, {
            onLogOut()
        })
    }

}