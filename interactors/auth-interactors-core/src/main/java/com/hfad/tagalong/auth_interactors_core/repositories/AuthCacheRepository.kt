package com.hfad.tagalong.auth_interactors_core.repositories

interface AuthCacheRepository {

    suspend fun saveSessionData()

    suspend fun loadSessionData()

    suspend fun deleteSessionData()

}