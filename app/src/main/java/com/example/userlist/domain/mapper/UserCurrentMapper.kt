package com.example.userlist.domain.mapper

import com.example.userlist.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, PresentationType> Flow<Resource<T>>.currentUserToPresentation(currentUser: (T) -> PresentationType): Flow<Resource<PresentationType>> {
    return map {
        when (it) {
            is Resource.Success -> Resource.Success(currentUser(it.data))
            is Resource.Error -> Resource.Error(it.errorMessage)
            is Resource.Loading -> Resource.Loading(false)
        }
    }
}