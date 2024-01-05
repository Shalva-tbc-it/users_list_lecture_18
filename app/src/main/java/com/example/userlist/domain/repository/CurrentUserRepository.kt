package com.example.userlist.domain.repository

import com.example.userlist.data.common.Resource
import com.example.userlist.domain.model.GetUsers
import kotlinx.coroutines.flow.Flow

interface CurrentUserRepository {
    suspend fun getCurrentUser(id: Int) : Flow<Resource<GetUsers>>
}