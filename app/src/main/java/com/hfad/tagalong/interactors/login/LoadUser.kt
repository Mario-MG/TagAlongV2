package com.hfad.tagalong.interactors.login

import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.domain.model.User
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.UserDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadUser(
    private val userService: RetrofitUserService,
    private val userDtoMapper: UserDtoMapper
) {

    fun execute(token: Token): Flow<DataState<User>> = flow {
        try {
            emit(DataState.Loading)

            val user = getUser(auth = getAuthorizationHeader(token))

            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private fun getAuthorizationHeader(token: Token) = "Bearer ${token.accessToken}"

    private suspend fun getUser(auth: String): User {
        return userDtoMapper.mapToDomainModel(userService.getUserProfile(auth = auth))
    }

}