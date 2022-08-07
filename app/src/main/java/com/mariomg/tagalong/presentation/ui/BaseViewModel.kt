package com.mariomg.tagalong.presentation.ui

import androidx.lifecycle.ViewModel
import com.mariomg.tagalong.R
import com.mariomg.tagalong.interactors_core.data.ErrorType
import com.mariomg.tagalong.interactors_core.data.ErrorType.CacheError
import com.mariomg.tagalong.interactors_core.data.ErrorType.NetworkError
import com.mariomg.tagalong.presentation.BaseApplication
import com.mariomg.tagalong.presentation.util.DialogQueue

abstract class BaseViewModel : ViewModel() {

    abstract val dialogQueue: DialogQueue

    protected fun appendGenericErrorToQueue(error: ErrorType) {
        when (error) {
            is CacheError -> dialogQueue.appendErrorDialog(BaseApplication.getString(R.string.generic_cache_error))
            is NetworkError -> dialogQueue.appendErrorDialog(BaseApplication.getString(R.string.generic_spotify_error))
        }
    }

}