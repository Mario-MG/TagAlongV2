package com.hfad.tagalong.network.repositories

import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.network.model.UserDtoMapper
import com.hfad.tagalong.network.session.SessionDataImpl
import com.hfad.tagalong.network.session.SessionManagerImpl

class AuthNetworkRepositoryImpl(
    private val authService: RetrofitAuthService,
    private val tokenMapper: TokenDtoMapper,
    private val userService: RetrofitUserService,
    private val userMapper: UserDtoMapper,
    private val sessionManager: SessionManagerImpl // TODO: How to abstract this?
) : AuthNetworkRepository {

    override suspend fun logIn(codeVerifier: String, code: String) {
        val token = authService.getNewToken(
            codeVerifier = codeVerifier,
            code = code
        )
        val user = userService.getUserProfile(auth = "Bearer ${token.accessToken}")
        sessionManager.logIn(
            SessionDataImpl(
                token = tokenMapper.mapToDomainModel(token),
                user = userMapper.mapToDomainModel(user)
            )
        )
    }

    override suspend fun refresh() {
        val token = authService.refreshToken(
            refreshToken = sessionManager.refreshToken()
        )
        sessionManager.refreshSession(token = tokenMapper.mapToDomainModel(token))
    }

    override suspend fun logOut() {
        sessionManager.logOut()
    }

}