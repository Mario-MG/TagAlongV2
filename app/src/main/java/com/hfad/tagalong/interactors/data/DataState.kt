package com.hfad.tagalong.interactors.data

sealed class DataState<out T> {

    data class Loading(val loading: Boolean) : DataState<Nothing>()

    data class Success<T>(val data: T) : DataState<T>()

    data class Error(val error: ErrorType) : DataState<Nothing>()

}