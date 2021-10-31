package com.hfad.tagalong.domain.model

data class Tag (
    val id: Long,
    val name: String
) {
    constructor(name: String) : this(0, name)

    override fun toString(): String {
        return name
    }
}