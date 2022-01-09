package com.hfad.tagalong.cache

import android.database.sqlite.SQLiteConstraintException
import com.hfad.tagalong.interactors.data.ErrorType
import com.hfad.tagalong.interactors.data.ErrorType.CacheError.DuplicateError
import com.hfad.tagalong.interactors.data.ErrorType.CacheError.UnknownCacheError
import com.hfad.tagalong.interactors.data.ErrorHandler

class CacheErrorHandler : ErrorHandler {

    override fun parseError(throwable: Throwable): ErrorType {
        return when (throwable) {
            is SQLiteConstraintException -> DuplicateError()
            else -> UnknownCacheError
        }
    }

}