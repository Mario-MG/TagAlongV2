package com.hfad.tagalong.interactors.login

import android.content.SharedPreferences
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveSessionInfo(
    private val sharedPreferences: SharedPreferences
) {

    fun execute(token: Token): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading)

            saveRefreshToken(token.refreshToken)

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit()
            .putString("refreshToken", refreshToken) // TODO: Extract String to Constants
            .apply()
    }

}