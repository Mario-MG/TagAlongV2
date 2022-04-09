package com.hfad.tagalong.auth_interactors

import com.hfad.tagalong.auth_interactors_core.session.SessionManager
import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
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