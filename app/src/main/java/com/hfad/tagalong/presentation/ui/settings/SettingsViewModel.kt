package com.hfad.tagalong.presentation.ui.settings

import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.auth_interactors.DeleteSessionData
import com.hfad.tagalong.auth_interactors.LogOut
import com.hfad.tagalong.interactors_core.util.on
import com.hfad.tagalong.presentation.BaseApplication
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
    private val deleteSessionData: DeleteSessionData,
    private val logOut: LogOut
) : BaseViewModel() {

    override val dialogQueue = DialogQueue()

    fun onTriggerEvent(event: SettingsEvent) {
        viewModelScope.launch {
            when (event) {
                is LogOutEvent -> {
                    deleteSessionDataAndLogOut()
                }
            }
        }
    }

    private fun deleteSessionDataAndLogOut() {
        deleteSessionData
            .execute()
            .on(
                success = { logOut() },
                error = { dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.logout_error_description)) }
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

}