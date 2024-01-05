package com.example.userlist.data.repository

import android.util.Log.d
import com.example.userlist.data.common.HandleResponse
import com.example.userlist.data.common.Resource
import com.example.userlist.data.mapper.currentUserToDomain
import com.example.userlist.data.mapper.toDomain
import com.example.userlist.data.service.ReqresApiService
import com.example.userlist.domain.model.GetUsers
import com.example.userlist.domain.repository.CurrentUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentUserRepositoryImpl @Inject constructor(
    private val reqresApiService: ReqresApiService,
    private val handleResponse: HandleResponse
) : CurrentUserRepository {
    override suspend fun getCurrentUser(id: Int): Flow<Resource<GetUsers>> {
        d("currentUser", "repositoryImpl $id")
        return handleResponse.apiCall { reqresApiService.getCurrentUser(id = id) }.currentUserToDomain {
            it.data.toDomain()
        }
    }
}