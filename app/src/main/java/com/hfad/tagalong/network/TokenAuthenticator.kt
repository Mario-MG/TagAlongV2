package com.hfad.tagalong.network

import com.hfad.tagalong.presentation.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

// Source: https://www.simplifiedcoding.net/retrofit-authenticator-refresh-token/
class TokenAuthenticator(
    private val sessionManager: SessionManager // TODO: Review if SessionManager should really be depended on here
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request { // TODO: Handle if refreshToken() is unsuccessful
        return runBlocking {
            sessionManager.refreshToken()
            response.request.newBuilder()
                .header("Authorization", sessionManager.getAuthorizationHeader())
                .build()
        }
    }

}