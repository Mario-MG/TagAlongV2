package com.mariomg.tagalong.presentation.ui.settings

import androidx.lifecycle.viewModelScope
import com.mariomg.tagalong.R
import com.mariomg.tagalong.auth_interactors.DeleteSessionData
import com.mariomg.tagalong.auth_interactors.LogOut
import com.mariomg.tagalong.interactors_core.util.on
import com.mariomg.tagalong.presentation.BaseApplication
import com.mariomg.tagalong.presentation.ui.BaseViewModel
import com.mariomg.tagalong.presentation.ui.settings.SettingsEvent.LogOutEvent
import com.mariomg.tagalong.presentation.util.DialogQueue
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
                error = { dialogQueue.appendErrorDialog(BaseApplication.getString(R.string.logout_error_description)) }
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