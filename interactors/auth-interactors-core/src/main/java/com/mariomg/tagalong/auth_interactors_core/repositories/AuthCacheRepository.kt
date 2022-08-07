package com.mariomg.tagalong.auth_interactors_core.repositories

import com.mariomg.tagalong.auth_interactors_core.session.SessionData

interface AuthCacheRepository {

    suspend fun saveSessionData(sessionData: SessionData)

    suspend fun loadSessionData(): SessionData?

    suspend fun deleteSessionData()

}