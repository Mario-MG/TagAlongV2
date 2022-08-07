package com.mariomg.tagalong.tag_domain

data class Tag(
    val id: Long,
    private val info: TagInfo
) {
    constructor(
        id: Long,
        name: String,
        size: Int
    ) : this(
        id = id,
        info = TagInfo(
            name = name,
            size = size
        )
    )

    val name: String
        get() = this.info.name

    val size: Int
        get() = this.info.size
}