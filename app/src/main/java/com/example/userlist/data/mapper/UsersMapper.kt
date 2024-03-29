package com.example.userlist.data.mapper

import com.example.userlist.data.model.UsersDTO
import com.example.userlist.domain.model.GetUsers

fun UsersDTO.toDomain() : GetUsers {
    return GetUsers(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}