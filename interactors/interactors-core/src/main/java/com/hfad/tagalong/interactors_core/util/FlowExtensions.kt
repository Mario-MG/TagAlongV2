package com.hfad.tagalong.interactors_core.util

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

fun <T> Flow<DataState<T>>.on(
    loading: (Boolean) -> Unit = {},
    error: (ErrorType) -> Unit = {},
    success: (T) -> Unit = {}
): Flow<DataState<T>> {
    return this.onEach { dataState ->
        when (dataState) {
            is Loading -> loading(dataState.loading)
            is Error -> error(dataState.error)
            is Success -> success(dataState.data)
        }
    }
}