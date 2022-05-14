package com.sid.assignment.data

sealed class NetworkResult<out T>
/*    (
    val data: T? = null,
    val message: String? = null
)*/
{

    data class Success<out T>(val data: T) : NetworkResult<T>()

    data class Error(val message: String) : NetworkResult<Nothing>()

    class Loading<T> : NetworkResult<T>()
}

