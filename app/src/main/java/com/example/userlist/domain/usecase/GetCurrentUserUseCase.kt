package com.example.userlist.domain.usecase

import com.example.userlist.data.common.Resource
import com.example.userlist.domain.mapper.currentUserToPresentation
import com.example.userlist.domain.mapper.toPresentation
import com.example.userlist.domain.repository.CurrentUserRepository
import com.example.userlist.presentation.model.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: CurrentUserRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<Users>> {
        return repository.getCurrentUser(id = id).currentUserToPresentation {
            it.toPresentation()
        }

    }
}