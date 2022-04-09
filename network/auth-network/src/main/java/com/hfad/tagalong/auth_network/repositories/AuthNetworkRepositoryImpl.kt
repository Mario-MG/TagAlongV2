package com.hfad.tagalong.auth_network.repositories

import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.auth_network.services.RetrofitAuthService
import com.hfad.tagalong.auth_network.services.RetrofitUserService
import com.hfad.tagalong.auth_network.model.TokenDtoMapper
import com.hfad.tagalong.auth_network.model.UserDtoMapper
import com.hfad.tagalong.session.SessionDataImpl

class AuthNetworkRepositoryImpl(
    private val authService: RetrofitAuthService,
    private val tokenMapper: TokenDtoMapper,
    private val userService: RetrofitUserService,
    private val userMapper: UserDtoMapper,
    private val clientId: String,
    private val redirectUri: String
) : AuthNetworkRepository {

    override suspend fun getNewSessionData(codeVerifier: String, code: String): SessionData {
        val token = authService.getNewToken(
            codeVerifier = codeVerifier,
            code = code,
            clientId = clientId,
            redirectUri = redirectUri
        )
        val user = userService.getUserProfile(auth = "Bearer ${token.accessToken}")
        return SessionDataImpl(
            token = tokenMapper.mapToDomainModel(token),
            user = userMapper.mapToDomainModel(user)
        )
    }

    override suspend fun refreshSessionData(oldSessionData: SessionData): SessionData {
        if (oldSessionData !is SessionDataImpl) throw IllegalArgumentException()
        val token = authService.refreshToken(
            refreshToken = oldSessionData.token.refreshToken,
            clientId = clientId
        )
        return SessionDataImpl(
            token = tokenMapper.mapToDomainModel(token),
            user = oldSessionData.user
        )
    }

}