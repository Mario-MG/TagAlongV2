package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.model.TokenDtoMapper

class AuthRepositoryImpl(
    private val authService: RetrofitAuthService,
    private val tokenMapper: TokenDtoMapper
): AuthRepository {

    override suspend fun getNewToken(
        clientId: String,
        codeVerifier: String,
        code: String,
        redirectUri: String
    ): Token {
        return tokenMapper.mapToDomainModel(
            authService.getNewToken(
                clientId = clientId,
                codeVerifier = codeVerifier,
                code = code,
                redirectUri = redirectUri
            )
        )
    }

    override suspend fun refreshToken(clientId: String, refreshToken: String): Token {
        return tokenMapper.mapToDomainModel(
            authService.refreshToken(
                clientId = clientId,
                refreshToken = refreshToken
            )
        )
    }
}