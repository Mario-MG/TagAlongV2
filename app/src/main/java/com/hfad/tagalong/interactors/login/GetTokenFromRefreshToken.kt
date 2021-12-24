package com.hfad.tagalong.interactors.login

import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.model.TokenDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTokenFromRefreshToken(
    private val authService: RetrofitAuthService,
    private val tokenDtoMapper: TokenDtoMapper
) {

    fun execute(clientId: String, refreshToken: String): Flow<DataState<Token>> = flow {
        try {
            emit(DataState.Loading)

            val token = refreshToken(
                clientId = clientId,
                refreshToken = refreshToken
            )

            emit(DataState.Success(token))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun refreshToken(clientId: String, refreshToken: String): Token {
        return tokenDtoMapper.mapToDomainModel(
            authService.refreshToken(
                clientId = clientId,
                refreshToken = refreshToken
            )
        )
    }

}