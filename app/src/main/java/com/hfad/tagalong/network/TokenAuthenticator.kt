package com.hfad.tagalong.network

import com.hfad.tagalong.Session
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

// Source: https://www.simplifiedcoding.net/retrofit-authenticator-refresh-token/
class TokenAuthenticator(
    private val session: Session // TODO: Review if session should really be depended on here
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request { // TODO: Handle if refreshToken() is unsuccessful
        return runBlocking {
            session.refreshToken()
            response.request.newBuilder()
                .header("Authorization", session.getAuthorizationHeader())
                .build()
        }
    }

}