package com.example.userlist.presentation.model

data class Users (
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val isSelect: Boolean = false,
    val errorStatus: Status
) {
    enum class Status() {
        SUCCESS,
        ERROR
    }
}

