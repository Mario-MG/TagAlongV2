package com.hfad.tagalong.cache.repositories

import android.content.Context
import androidx.preference.PreferenceManager
import com.hfad.tagalong.settings_interactors_core.repositories.SettingsRepository

class SettingsRepositoryImpl(
    context: Context
) : SettingsRepository {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun loadStayLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(STAY_LOGGED_IN, false)
    }

    override suspend fun saveStayLoggedIn(stayLoggedIn: Boolean) {
        sharedPreferences.edit()
            .putBoolean(STAY_LOGGED_IN, stayLoggedIn)
            .apply()
    }

    companion object {
        private const val STAY_LOGGED_IN = "stayLoggedIn"
    }

}