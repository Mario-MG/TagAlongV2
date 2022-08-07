package com.mariomg.tagalong.settings_interactors_core.repositories

interface SettingsRepository {

    suspend fun loadStayLoggedIn(): Boolean

    suspend fun saveStayLoggedIn(stayLoggedIn: Boolean)

}