package com.hfad.tagalong.auth_interactors_core.repositories

import com.hfad.tagalong.auth_interactors_core.session.SessionData

interface AuthCacheRepository {

    suspend fun saveSessionData(sessionData: SessionData)

    suspend fun loadSessionData(): SessionData?

    suspend fun deleteSessionData()

}