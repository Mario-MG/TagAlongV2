package com.hfad.tagalong.interactors_core.data

interface ErrorMapper {

    fun parseError(throwable: Throwable): ErrorType

}