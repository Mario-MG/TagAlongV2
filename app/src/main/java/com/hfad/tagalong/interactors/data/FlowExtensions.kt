package com.hfad.tagalong.interactors.data

import com.hfad.tagalong.interactors.data.DataState.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

fun <T> Flow<DataState<T>>.on(
    loadingStateChange: (Boolean) -> Unit = {},
    error: (ErrorType) -> Unit = {},
    success: (T) -> Unit = {}
): Flow<DataState<T>> {
    return this.onEach { dataState ->
        loadingStateChange(dataState is Loading)
        when (dataState) {
            is Success -> success(dataState.data)
            is Error -> error(dataState.error)
        }
    }
}