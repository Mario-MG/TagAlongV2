package com.hfad.tagalong.network.models

import com.google.gson.annotations.SerializedName

data class PlaylistTracksRef (
    @SerializedName("href") var href : String,
    @SerializedName("total") var total : Int
)
