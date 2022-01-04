package com.hfad.tagalong.interactors.data

sealed class DataState<out T> private constructor(
    open val data: T? = null,
    open val error: ErrorType? = null
) {

    object Loading : DataState<Nothing>()

    data class Success<T>(override val data: T) : DataState<T>(data = data)

    data class Error(override val error: ErrorType) : DataState<Nothing>(error = error)

}