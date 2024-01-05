package com.example.userlist.domain.model

data class GetUsers (
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)