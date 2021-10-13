package com.hfad.tagalong.network.models

import com.google.gson.annotations.SerializedName

internal data class User (
    @SerializedName("display_name") var displayName : String?,
    @SerializedName("href") var href : String,
    @SerializedName("id") var id : String,
    @SerializedName("type") var type : String,
    @SerializedName("uri") var uri : String
)
