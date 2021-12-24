package com.hfad.tagalong.interactors.settings

import android.content.SharedPreferences
import com.hfad.tagalong.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadStayLoggedIn(
    private val sharedPreferences: SharedPreferences
) {

    fun execute(): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)

            val stayLoggedIn = loadStayLoggedIn()

            emit(DataState.Success(stayLoggedIn))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private fun loadStayLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("stayLoggedIn", true) // TODO: Move String to Constants
    }

}