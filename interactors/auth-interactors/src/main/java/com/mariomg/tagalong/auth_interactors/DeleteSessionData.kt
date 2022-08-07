package com.mariomg.tagalong.auth_interactors

import com.mariomg.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteSessionData(
    private val authCacheRepository: AuthCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            authCacheRepository.deleteSessionData()

            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}