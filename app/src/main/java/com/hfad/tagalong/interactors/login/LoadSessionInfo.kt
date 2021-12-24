package com.hfad.tagalong.interactors.login

import android.content.SharedPreferences
import com.hfad.tagalong.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadSessionInfo(
    private val sharedPreferences: SharedPreferences
) {

    fun execute(): Flow<DataState<String?>> = flow {
        try {
            emit(DataState.Loading)

            val refreshToken = loadRefreshToken()

            emit(DataState.Success(refreshToken))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private fun loadRefreshToken(): String? {
        return sharedPreferences.getString("refreshToken", null) // TODO: Extract String to Constants
    }

}