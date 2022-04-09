package com.hfad.tagalong.network_core.model

import com.google.gson.annotations.SerializedName

data class UserDto (
    @SerializedName("display_name") var displayName : String?,
    @SerializedName("href") var href : String,
    @SerializedName("id") var id : String,
    @SerializedName("type") var type : String,
    @SerializedName("uri") var uri : String
)
