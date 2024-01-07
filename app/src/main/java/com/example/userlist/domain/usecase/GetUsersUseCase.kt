package com.example.userlist.domain.usecase

import com.example.userlist.data.common.Resource
import com.example.userlist.domain.mapper.listToPresentation
import com.example.userlist.domain.mapper.toPresentation
import com.example.userlist.domain.repository.UsersRepository
import com.example.userlist.presentation.model.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUsersUseCase @Inject constructor(
    private val repository: UsersRepository
){
    suspend operator fun invoke(): Flow<Resource<List<Users>>> {
        return repository.getUsers().listToPresentation {
            it.toPresentation()
        }
    }
}