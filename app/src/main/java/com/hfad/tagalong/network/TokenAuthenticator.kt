package com.hfad.tagalong.network

import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.presentation.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException

// Source: https://www.simplifiedcoding.net/retrofit-authenticator-refresh-token/
class TokenAuthenticator(
    private val sessionManager: SessionManager, // TODO: Review if SessionManager should really be depended on here
    private val authService: RetrofitAuthService,
    private val tokenDtoMapper: TokenDtoMapper,
    private val clientId: String
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        return runBlocking {
            val token = getNewToken()
            sessionManager.refreshToken(token)
            response.request.newBuilder()
                .header("Authorization", sessionManager.getAuthorizationHeader())
                .build()
        }
    }

    private suspend fun getNewToken(): Token {
        return tokenDtoMapper.mapToDomainModel(
            authService.refreshToken(
                clientId = clientId,
                refreshToken = sessionManager.refreshToken
            )
        ) // TODO: Handle unsuccessful refresh
    }

}