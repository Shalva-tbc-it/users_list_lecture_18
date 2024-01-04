package com.example.userlist.data.mapper

import com.example.userlist.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, Domain> Flow<Resource<List<T>>>.listToDomain(toList: (T) -> Domain): Flow<Resource<List<Domain>>> {
    return map {
        when (it) {
            is Resource.Success -> Resource.Success(it.data.map(toList))
            is Resource.Error -> Resource.Error(it.errorMessage)
            is Resource.Loading -> Resource.Loading(false)
        }
    }
}