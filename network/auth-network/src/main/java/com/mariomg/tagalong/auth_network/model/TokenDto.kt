package com.mariomg.tagalong.auth_network.model

import com.google.gson.annotations.SerializedName

data class TokenDto (
    @SerializedName("access_token") var accessToken : String,
    @SerializedName("token_type") var tokenType : String,
    @SerializedName("expires_in") var expiresIn : Int,
    @SerializedName("scope") var scope : String,
    @SerializedName("refresh_token") var refreshToken : String
)