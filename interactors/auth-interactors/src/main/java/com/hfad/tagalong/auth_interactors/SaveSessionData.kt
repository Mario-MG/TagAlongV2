package com.hfad.tagalong.auth_interactors

import com.hfad.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveSessionData(
    private val authCacheRepository: AuthCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(sessionData: SessionData): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            authCacheRepository.saveSessionData(sessionData)

            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}