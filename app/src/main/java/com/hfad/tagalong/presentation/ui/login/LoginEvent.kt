package com.hfad.tagalong.presentation.ui.login

import android.net.Uri

sealed class LoginEvent {

    data class ReceiveLoginCodeEvent(val uri: Uri) : LoginEvent()

}
