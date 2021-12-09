package com.hfad.tagalong.domain.model

import com.hfad.tagalong.presentation.components.Keyword

data class Tag(
    val id: Long,
    val name: String,
    val size: Int
) : Keyword {
    constructor(name: String) : this(
        id = 0,
        name = name,
        size = 0
    )

    override fun value(): String {
        return name
    }
}