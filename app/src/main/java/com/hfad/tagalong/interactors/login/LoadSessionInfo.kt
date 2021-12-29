package com.hfad.tagalong.interactors.login

import android.content.SharedPreferences
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadSessionInfo(
    private val sharedPreferences: SharedPreferences,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading)

            val refreshToken = loadRefreshToken()

            emit(DataState.Success(refreshToken))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        }
    }

    private fun loadRefreshToken(): String {
        return sharedPreferences.getString("refreshToken", "") ?: "" // TODO: Extract String to Constants
    }

}