package com.hfad.tagalong.network.model

import com.google.gson.annotations.SerializedName

data class PlaylistItemsPage (
    @SerializedName("href") var href : String,
    @SerializedName("items") var items : List<PlaylistItem>,
    @SerializedName("limit") var limit : Int,
    @SerializedName("next") var next : String?,
    @SerializedName("offset") var offset : Int,
    @SerializedName("previous") var previous : String?,
    @SerializedName("total") var total : Int
)