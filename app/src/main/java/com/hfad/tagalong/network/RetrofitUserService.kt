package com.hfad.tagalong.network

import com.hfad.tagalong.network.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitUserService {

    @GET("me")
    suspend fun getUserProfile(
        @Header("Authorization") auth: String
    ): UserDto

}