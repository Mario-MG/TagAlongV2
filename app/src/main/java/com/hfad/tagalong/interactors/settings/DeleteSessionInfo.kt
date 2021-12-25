package com.hfad.tagalong.interactors.settings

import android.content.SharedPreferences
import com.hfad.tagalong.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteSessionInfo(
    private val sharedPreferences: SharedPreferences
) {

    fun execute(): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading)

            deleteRefreshToken()

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private fun deleteRefreshToken() {
        sharedPreferences.edit()
            .remove("refreshToken") // TODO: Extract String to Constants
            .apply()
    }

}