package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.User
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.UserDtoMapper

class UserRepositoryImpl(
    private val userService: RetrofitUserService,
    private val userMapper: UserDtoMapper
) : UserRepository {

    override suspend fun get(auth: String): User {
        return userMapper.mapToDomainModel(userService.getUserProfile(auth = auth))
    }

}