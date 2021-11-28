package com.hfad.tagalong.repository

interface SettingsRepository {

    suspend fun saveStayLoggedIn(value: Boolean)

    suspend fun loadStayLoggedIn(): Boolean

}