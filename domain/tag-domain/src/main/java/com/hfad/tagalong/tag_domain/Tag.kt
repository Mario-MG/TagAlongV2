package com.hfad.tagalong.tag_domain

data class Tag(
    val id: Long,
    val name: String,
    val size: Int // TODO: Should this field be part of the domain?
)