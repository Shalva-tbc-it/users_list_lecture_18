package com.example.userlist.domain.mapper

import com.example.userlist.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, PresentationType> Flow<Resource<List<T>>>.listToPresentation(toList: (T) -> PresentationType): Flow<Resource<List<PresentationType>>> {
    return map {
        when (it) {
            is Resource.Success -> Resource.Success(it.data.map(toList))
            is Resource.Error -> Resource.Error(it.errorMessage)
            is Resource.Loading -> Resource.Loading()
        }
    }
}