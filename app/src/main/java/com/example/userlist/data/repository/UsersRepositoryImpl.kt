package com.example.userlist.data.repository

import com.example.userlist.data.common.HandleResponse
import com.example.userlist.data.common.Resource
import com.example.userlist.data.mapper.listToDomain
import com.example.userlist.data.mapper.toDomain
import com.example.userlist.data.service.MockyApiService
import com.example.userlist.domain.model.GetUsers
import com.example.userlist.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: MockyApiService,
    private val handleResponse: HandleResponse
) : UsersRepository {

    override suspend fun getUsers(): Flow<Resource<List<GetUsers>>> {
        return handleResponse.safeApiCall { apiService.getUsers() }
            .listToDomain { it.toDomain() }

    }

}
