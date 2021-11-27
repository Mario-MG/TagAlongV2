package com.hfad.tagalong.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import com.hfad.tagalong.presentation.ui.settings.SettingsEvent.LogOutEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val session: Session
) : ViewModel() {

    fun onTriggerEvent(event: SettingsEvent) {
        viewModelScope.launch {
            when (event) {
                is LogOutEvent -> {
                    logOut()
                }
            }
        }
    }

    private suspend fun logOut() {
        session.logOut()
    }

}