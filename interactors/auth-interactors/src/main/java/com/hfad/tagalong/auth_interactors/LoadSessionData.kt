package com.hfad.tagalong.auth_interactors

import com.hfad.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadSessionData(
    private val authCacheRepository: AuthCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            authCacheRepository.loadSessionData()

            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}