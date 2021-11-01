package com.hfad.tagalong.network

import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.network.model.TokenDto
import retrofit2.http.*

interface RetrofitAuthService {

    @POST("token")
    @FormUrlEncoded
    suspend fun getNewToken(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("code") code: String
    ): TokenDto

    @POST("token")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET, // TODO: For testing purposes only TO BE REMOVED
        @Field("refresh_token") refreshToken: String
    ): TokenDto

}