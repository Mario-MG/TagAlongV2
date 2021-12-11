package com.hfad.tagalong.presentation.ui.settings

sealed class SettingsEvent {

    data class LogOutEvent(val callback: () -> Unit = {}) : SettingsEvent()

}
