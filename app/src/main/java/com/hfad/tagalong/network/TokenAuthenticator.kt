package com.hfad.tagalong.network

import android.content.Context
import com.hfad.tagalong.Session
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val session: Session
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request { // TODO: Handle if refreshToken() is unsuccessful
        return runBlocking {
            session.refreshToken()
            response.request.newBuilder()
                .header("Authorization", "Bearer ${session.token}")
                .build()
        }
    }

}