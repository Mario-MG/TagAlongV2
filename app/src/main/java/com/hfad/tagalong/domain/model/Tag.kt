package com.hfad.tagalong.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.hfad.tagalong.presentation.components.Keyword

data class Tag(
    val id: Long,
    val name: String,
    val size: Int
) : Keyword, Parcelable {

    constructor(name: String) : this(
        id = 0,
        name = name,
        size = 0
    )

    constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        name = parcel.readString()!!,
        size = parcel.readInt()
    )

    override fun value(): String {
        return name
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeLong(id)
            writeString(name)
            writeInt(size)
        }
    }

    companion object CREATOR : Parcelable.Creator<Tag> {
        override fun createFromParcel(parcel: Parcel): Tag {
            return Tag(parcel)
        }

        override fun newArray(size: Int): Array<Tag?> {
            return arrayOfNulls(size)
        }
    }


}