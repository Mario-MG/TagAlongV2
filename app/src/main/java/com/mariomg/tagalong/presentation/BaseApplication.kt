package com.mariomg.tagalong.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {

        @SuppressLint("StaticFieldLeak") // Source: https://stackoverflow.com/questions/4410328/how-to-obtain-assetmanager-without-reference-to-context/4410349#comment53836034_4535727
        private lateinit var mContext: Context

        // Source: https://stackoverflow.com/a/4410349/6100280
        fun getContext() = mContext

        fun getString(@StringRes stringResId: Int): String = mContext.getString(stringResId)

    }
}