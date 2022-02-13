package com.hfad.tagalong.auth_interactors

import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.auth_interactors_core.session.SessionData
import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewSessionData(
    private val authNetworkRepository: AuthNetworkRepository,
    private val networkErrorMapper: ErrorMapper
) {

    fun execute(
        codeVerifier: String,
        code: String
    ): Flow<DataState<SessionData>> = flow {
        try {
            emit(Loading(true))

            val sessionData = authNetworkRepository.getNewSessionData(
                codeVerifier = codeVerifier,
                code = code
            )

            emit(Success(sessionData))
        } catch (e: Exception) {
            emit(Error(networkErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}