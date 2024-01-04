package com.example.userlist.data.mapper

import com.example.userlist.data.model.UsersDTO
import com.example.userlist.domain.model.Users

fun UsersDTO.toDomain() : Users {
    return Users(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}