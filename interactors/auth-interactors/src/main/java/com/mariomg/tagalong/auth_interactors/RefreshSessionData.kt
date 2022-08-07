package com.mariomg.tagalong.auth_interactors

import com.mariomg.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.mariomg.tagalong.auth_interactors_core.session.SessionData
import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RefreshSessionData(
    private val authNetworkRepository: AuthNetworkRepository,
    private val networkErrorMapper: ErrorMapper
) {

    fun execute(oldSessionData: SessionData): Flow<DataState<SessionData>> = flow {
        try {
            emit(Loading(true))

            val newSessionData = authNetworkRepository.refreshSessionData(oldSessionData)

            emit(Success(newSessionData))
        } catch (e: Exception) {
            emit(Error(networkErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}