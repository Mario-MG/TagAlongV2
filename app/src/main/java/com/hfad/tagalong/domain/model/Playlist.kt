package com.hfad.tagalong.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Playlist(
    val id: String,
    val name: String,
    val size: Int,
    val imageUrl: String? = null
) : Parcelable {

    constructor(name: String) : this(
        id = "",
        name = name,
        size = 0
    )

    constructor(parcel: Parcel) : this(
        id = parcel.readString()!!,
        name = parcel.readString()!!,
        size = parcel.readInt(),
        imageUrl = parcel.readString()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeString(id)
            writeString(name)
            writeInt(size)
            writeString(imageUrl)
        }
    }

    companion object CREATOR : Parcelable.Creator<Playlist> {
        override fun createFromParcel(parcel: Parcel): Playlist {
            return Playlist(parcel)
        }

        override fun newArray(size: Int): Array<Playlist?> {
            return arrayOfNulls(size)
        }
    }
}
