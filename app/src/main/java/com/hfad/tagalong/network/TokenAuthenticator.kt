package com.hfad.tagalong.network

import com.hfad.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.auth_interactors_core.session.SessionManager
import com.hfad.tagalong.network.util.AuthManager
import com.hfad.tagalong.settings_interactors_core.repositories.SettingsRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException

// Source: https://www.simplifiedcoding.net/retrofit-authenticator-refresh-token/
class TokenAuthenticator(
    private val authNetworkRepository: AuthNetworkRepository,
    private val authManager: AuthManager,
    private val authCacheRepository: AuthCacheRepository,
    private val settingsRepository: SettingsRepository,
    private val sessionManager: SessionManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        return runBlocking {
            tryToRefresh()
            response.request.newBuilder()
                .header("Authorization", "Bearer ${authManager.accessToken()}")
                .build()
        }
    }

    private suspend fun tryToRefresh() {
        try {
            val newSessionData = authNetworkRepository.refreshSessionData(sessionManager.sessionData!!)
            val stayLoggedIn = settingsRepository.loadStayLoggedIn()
            if (stayLoggedIn) {
                authCacheRepository.saveSessionData(newSessionData)
            }
            sessionManager.refresh(newSessionData)
        } catch (e: Exception) {
            authCacheRepository.deleteSessionData()
            sessionManager.logOut()
            throw IOException("Re-authorization unsuccessful") // TODO: Find a more specific child of IOException (it must be an IOException)
        }
    }

}