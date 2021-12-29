package com.hfad.tagalong.interactors.data

sealed class DataState<out T> private constructor(
    open val data: T? = null,
    open val error: ErrorType? = null,
    open val loading: Boolean = false
) {

    object Loading : DataState<Nothing>(loading = true)

    data class Success<T>(override val data: T) : DataState<T>(data = data)

    data class Error(override val error: ErrorType) : DataState<Nothing>(error = error)

}