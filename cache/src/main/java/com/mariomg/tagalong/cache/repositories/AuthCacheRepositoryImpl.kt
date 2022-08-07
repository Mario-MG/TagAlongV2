package com.mariomg.tagalong.cache.repositories

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mariomg.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.mariomg.tagalong.auth_interactors_core.session.SessionData
import com.mariomg.tagalong.auth_interactors_core.session.SessionDataSerializer

class AuthCacheRepositoryImpl(
    context: Context,
    private val sessionDataSerializer: SessionDataSerializer
) : AuthCacheRepository {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "auth-shared-prefs",
        MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveSessionData(sessionData: SessionData) {
        sharedPreferences.edit()
            .putString(SESSION_DATA, sessionDataSerializer.serializeSessionData(sessionData))
            .apply()
    }

    override suspend fun loadSessionData(): SessionData? {
        return sharedPreferences.getString(SESSION_DATA, null)?.let { serializedSessionData ->
            sessionDataSerializer.deserializeSessionData(serializedSessionData)
        }
    }

    override suspend fun deleteSessionData() {
        sharedPreferences.edit()
            .remove(SESSION_DATA)
            .apply()
    }

    companion object {
        private const val SESSION_DATA = "sessionData"
    }

}