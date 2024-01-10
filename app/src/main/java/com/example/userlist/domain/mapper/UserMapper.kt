package com.example.userlist.domain.mapper

import com.example.userlist.domain.model.GetUsers
import com.example.userlist.presentation.model.Users

fun GetUsers.toPresentation() : Users {
    return Users(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar,
        isSelect = false,
        errorStatus = Users.Status.SUCCESS
    )
}