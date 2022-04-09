package com.hfad.tagalong.cache

import android.database.sqlite.SQLiteConstraintException
import com.hfad.tagalong.interactors_core.data.ErrorType
import com.hfad.tagalong.interactors_core.data.ErrorType.CacheError.DuplicateError
import com.hfad.tagalong.interactors_core.data.ErrorType.CacheError.UnknownCacheError
import com.hfad.tagalong.interactors_core.data.ErrorMapper

class CacheErrorMapper : ErrorMapper {

    override fun parseError(throwable: Throwable): ErrorType {
        return when (throwable) {
            is SQLiteConstraintException -> DuplicateError()
            else -> UnknownCacheError
        }
    }

}