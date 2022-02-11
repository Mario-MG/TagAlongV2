package com.hfad.tagalong.cache.repositories

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.hfad.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.session.SessionDataSerializer
import com.hfad.tagalong.session.SessionManager

class AuthCacheRepositoryImpl(
    application: BaseApplication,
    private val sessionManager: SessionManager, // TODO: Decouple?
    private val sessionDataSerializer: SessionDataSerializer
) : AuthCacheRepository {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        application,
        "auth-shared-prefs",
        MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveSessionData() {
        sessionManager.sessionData?.let { sessionData ->
            sharedPreferences.edit()
                .putString(SESSION_DATA, sessionDataSerializer.serializeSessionData(sessionData))
                .apply()
        }
    }

    override suspend fun loadSessionData() {
        sharedPreferences.getString(SESSION_DATA, null)?.let { serializedSessionData ->
            sessionManager.logIn(sessionDataSerializer.deserializeSessionData(serializedSessionData))
        } ?: run {
            sessionManager.logOut() // TODO: Review/improve this
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