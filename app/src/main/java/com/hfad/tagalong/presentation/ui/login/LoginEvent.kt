package com.hfad.tagalong.presentation.ui.login

import android.content.Context
import android.net.Uri

sealed class LoginEvent {

    object InitLoginScreenEvent : LoginEvent()

    data class ClickLoginButtonEvent(val context: Context) : LoginEvent()

    data class ReceiveLoginCodeEvent(val uri: Uri) : LoginEvent()

}
