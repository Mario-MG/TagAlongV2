package com.hfad.tagalong.presentation.ui.settings

import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.settings.DeleteSessionInfo
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.settings.SettingsEvent.LogOutEvent
import com.hfad.tagalong.presentation.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val deleteSessionInfo: DeleteSessionInfo,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    override val dialogQueue = DialogQueue()

    fun onTriggerEvent(event: SettingsEvent) {
        viewModelScope.launch {
            when (event) {
                is LogOutEvent -> {
                    logOut()
                }
            }
        }
    }

    private fun logOut() {
        deleteSessionInfo
            .execute()
            .on(
                success = { sessionManager.logOut() },
                error = { dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.logout_error_description)) }
            )
            .launchIn(viewModelScope)

    }

}