package com.example.userlist.domain.repository

import com.example.userlist.data.common.Resource
import com.example.userlist.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getUsers(): Flow<Resource<List<Users>>>
}

