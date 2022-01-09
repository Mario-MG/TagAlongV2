package com.hfad.tagalong.interactors.login

import android.content.SharedPreferences
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveSessionInfo(
    private val sharedPreferences: SharedPreferences,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(token: Token): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading(true))

            saveRefreshToken(token.refreshToken)

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit()
            .putString("refreshToken", refreshToken) // TODO: Extract String to Constants
            .apply()
    }

}