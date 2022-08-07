package com.mariomg.tagalong.auth_interactors_core.repositories

import com.mariomg.tagalong.auth_interactors_core.session.SessionData

interface AuthNetworkRepository {

    suspend fun getNewSessionData(codeVerifier: String, code: String): SessionData

    suspend fun refreshSessionData(oldSessionData: SessionData): SessionData

}