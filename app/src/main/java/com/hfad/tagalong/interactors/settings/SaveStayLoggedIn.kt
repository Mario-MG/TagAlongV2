package com.hfad.tagalong.interactors.settings

import android.content.SharedPreferences
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveStayLoggedIn(
    private val sharedPreferences: SharedPreferences,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(stayLoggedIn: Boolean): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading)

            saveStayLoggedIn(stayLoggedIn)

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        }
    }

    private fun saveStayLoggedIn(stayLoggedIn: Boolean) {
        sharedPreferences.edit()
            .putBoolean("stayLoggedIn", stayLoggedIn) // TODO: Move String to Constants
            .apply()
    }

}