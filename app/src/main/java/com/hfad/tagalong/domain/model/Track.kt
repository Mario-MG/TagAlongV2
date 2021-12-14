package com.hfad.tagalong.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Track(
    val id: String,
    val name: String,
    val album: String,
    val artists: List<String>,
    val imageUrl: String?,
    val uri: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString()!!,
        name = parcel.readString()!!,
        album = parcel.readString()!!,
        artists = parcel.createStringArrayList()!!,
        imageUrl = parcel.readString(),
        uri = parcel.readString()!!
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeString(id)
            writeString(name)
            writeString(album)
            writeStringList(artists)
            writeString(imageUrl)
            writeString(uri)
        }
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(parcel)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }

}