package com.mariomg.tagalong.auth_interactors

import com.mariomg.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.mariomg.tagalong.auth_interactors_core.session.SessionData
import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadSessionData(
    private val authCacheRepository: AuthCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<SessionData?>> = flow {
        try {
            emit(Loading(true))

            val sessionData = authCacheRepository.loadSessionData()

            emit(Success(sessionData))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}