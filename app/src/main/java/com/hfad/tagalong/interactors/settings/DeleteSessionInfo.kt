package com.hfad.tagalong.interactors.settings

import android.content.SharedPreferences
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteSessionInfo(
    private val sharedPreferences: SharedPreferences,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading)

            deleteRefreshToken()

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        }
    }

    private fun deleteRefreshToken() {
        sharedPreferences.edit()
            .remove("refreshToken") // TODO: Extract String to Constants
            .apply()
    }

}