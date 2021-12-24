package com.hfad.tagalong.interactors.login

import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.model.TokenDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTokenFromCode(
    private val authService: RetrofitAuthService,
    private val tokenDtoMapper: TokenDtoMapper
) {

    fun execute(
        clientId: String,
        codeVerifier: String,
        code: String,
        redirectUri: String
    ): Flow<DataState<Token>> = flow {
        try {
            emit(DataState.Loading)

            val token = getNewToken(
                clientId = clientId,
                codeVerifier = codeVerifier,
                code = code,
                redirectUri = redirectUri
            )

            emit(DataState.Success(token))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getNewToken(
        clientId: String,
        codeVerifier: String,
        code: String,
        redirectUri: String
    ): Token {
        return tokenDtoMapper.mapToDomainModel(
            authService.getNewToken(
                clientId = clientId,
                codeVerifier = codeVerifier,
                code = code,
                redirectUri = redirectUri
            )
        )
    }

}