package com.example.userlist.data.common

sealed class Resource<T> {
    data class Loading<T>(var boolean: Boolean) : Resource<T>()
    data class Success<T>(val data : T) : Resource<T>()
    data class Error<T>(val errorMessage : String) : Resource<T>()
}