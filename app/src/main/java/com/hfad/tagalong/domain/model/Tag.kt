package com.hfad.tagalong.domain.model

data class Tag(
    val id: Long,
    val name: String,
    val size: Int
) {
    constructor(name: String) : this(
        id = 0,
        name = name,
        size = 0
    )

    override fun toString(): String {
        return name
    }
}