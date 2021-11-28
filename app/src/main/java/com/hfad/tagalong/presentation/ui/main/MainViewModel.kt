package com.hfad.tagalong.presentation.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor (
    private val session: Session
) : ViewModel() {

    val isLoggedIn = mutableStateOf(session.isLoggedIn)

    init {
        viewModelScope.launch {
            initSession()
        }
    }

    private suspend fun initSession() {
        session.addLoginStatusObserver {
            isLoggedIn.value = it
        }
        session.init()
    }

    fun addLoginSuccessListener(listener: () -> Unit) {
        session.addLoginStatusObserver { success ->
            if (success) {
                listener()
            }
        }
    }

}