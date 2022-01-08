package com.hfad.tagalong.interactors.settings

import android.content.SharedPreferences
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadStayLoggedIn(
    private val sharedPreferences: SharedPreferences,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading(true))

            val stayLoggedIn = loadStayLoggedIn()

            emit(DataState.Success(stayLoggedIn))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private fun loadStayLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("stayLoggedIn", true) // TODO: Move String to Constants
    }

}