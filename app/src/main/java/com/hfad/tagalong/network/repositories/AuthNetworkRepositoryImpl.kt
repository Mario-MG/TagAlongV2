package com.hfad.tagalong.network.repositories

import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.network.model.UserDtoMapper
import com.hfad.tagalong.network.session.SessionDataImpl

class AuthNetworkRepositoryImpl(
    private val authService: RetrofitAuthService,
    private val tokenMapper: TokenDtoMapper,
    private val userService: RetrofitUserService,
    private val userMapper: UserDtoMapper
) : AuthNetworkRepository {

    override suspend fun getNewSessionData(codeVerifier: String, code: String): SessionData {
        val token = authService.getNewToken(
            codeVerifier = codeVerifier,
            code = code
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
            refreshToken = oldSessionData.token.refreshToken
        )
        return SessionDataImpl(
            token = tokenMapper.mapToDomainModel(token),
            user = oldSessionData.user
        )
    }

}