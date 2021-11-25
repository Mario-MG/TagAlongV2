package com.hfad.tagalong.repository

import android.content.SharedPreferences
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.model.TokenDtoMapper

class AuthRepositoryImpl(
    private val authService: RetrofitAuthService,
    private val tokenMapper: TokenDtoMapper,
    private val sharedPreferences: SharedPreferences
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

    override suspend fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit()
            .putString(SHARED_PREFS_REFRESH_TOKEN, refreshToken)
            .apply()
    }

    override suspend fun loadRefreshToken(): String? {
        return sharedPreferences.getString(SHARED_PREFS_REFRESH_TOKEN, null)
    }

    companion object {

        const val SHARED_PREFS_REFRESH_TOKEN = "refreshToken"

    }

}