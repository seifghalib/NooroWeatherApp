package com.example.nooro.utils

sealed class ApiState<out T> {

    data class Success<out R>(val data: R) : ApiState<R>()
    data class Failure(val msg:String) : ApiState<Nothing>()
    data object Loading : ApiState<Nothing>()

}