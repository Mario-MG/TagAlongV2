package com.hfad.tagalong.repository

import android.content.Context
import androidx.preference.PreferenceManager

class SettingsRepositoryImpl(
    context: Context
) : SettingsRepository {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun saveStayLoggedIn(value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(SHARED_PREFS_STAY_LOGGED_IN, value)
            .apply()
    }

    override suspend fun loadStayLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(SHARED_PREFS_STAY_LOGGED_IN, true)
    }

    companion object {
        private const val SHARED_PREFS_STAY_LOGGED_IN = "stayLoggedIn"
    }

}