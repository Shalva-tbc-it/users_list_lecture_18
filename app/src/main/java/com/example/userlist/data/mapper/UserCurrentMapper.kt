package com.example.userlist.data.mapper

import com.example.userlist.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, Domain> Flow<Resource<T>>.currentUserToDomain(currentUser: (T) -> Domain): Flow<Resource<Domain>> {
    return map {
        when (it) {
            is Resource.Success -> Resource.Success(currentUser(it.data))
            is Resource.Error -> Resource.Error(it.errorMessage)
            is Resource.Loading -> Resource.Loading(false)
        }
    }
}