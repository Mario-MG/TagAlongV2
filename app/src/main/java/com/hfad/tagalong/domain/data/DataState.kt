package com.hfad.tagalong.domain.data

sealed class DataState<out T> private constructor(
    open val data: T? = null,
    open val error: String? = null,
    open val loading: Boolean = false
) {

    data class Success<T>(override val data: T) : DataState<T>(data = data)

    data class Error(override val error: String) : DataState<Nothing>(error = error)

    object Loading : DataState<Nothing>(loading = true)

}