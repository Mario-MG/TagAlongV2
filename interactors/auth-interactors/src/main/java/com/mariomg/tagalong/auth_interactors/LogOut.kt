package com.mariomg.tagalong.auth_interactors

import com.mariomg.tagalong.auth_interactors_core.session.SessionManager
import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LogOut(
    private val sessionManager: SessionManager
) {

    fun execute(): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            sessionManager.logOut()

            emit(Success(Unit))
        } catch (e: Exception) {
            // TODO
        } finally {
            emit(Loading(false))
        }
    }

}