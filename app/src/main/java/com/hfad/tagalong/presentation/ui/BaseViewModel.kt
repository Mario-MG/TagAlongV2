package com.hfad.tagalong.presentation.ui

import androidx.lifecycle.ViewModel
import com.hfad.tagalong.R
import com.hfad.tagalong.interactors_core.data.ErrorType
import com.hfad.tagalong.interactors_core.data.ErrorType.CacheError
import com.hfad.tagalong.interactors_core.data.ErrorType.NetworkError
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.util.DialogQueue
import com.hfad.tagalong.interactors.data.ErrorType as LegacyErrorType
import com.hfad.tagalong.interactors.data.ErrorType.CacheError as LegacyCacheError
import com.hfad.tagalong.interactors.data.ErrorType.NetworkError as LegacyNetworkError

abstract class BaseViewModel : ViewModel() {

    abstract val dialogQueue: DialogQueue

    protected fun appendGenericErrorToQueue(error: LegacyErrorType) {
        when (error) {
            is LegacyCacheError -> dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.generic_cache_error))
            is LegacyNetworkError -> dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.generic_spotify_error))
        }
    }

    protected fun appendGenericErrorToQueue(error: ErrorType) {
        when (error) {
            is CacheError -> dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.generic_cache_error))
            is NetworkError -> dialogQueue.appendErrorDialog(BaseApplication.getContext().getString(R.string.generic_spotify_error))
        }
    }

}