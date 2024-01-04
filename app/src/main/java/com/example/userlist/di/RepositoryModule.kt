package com.example.userlist.di

//import com.example.userlist.data.repository.CurrentUserRepositoryImpl
import com.example.userlist.data.common.HandleResponse
import com.example.userlist.data.repository.UsersRepositoryImpl
import com.example.userlist.data.service.MockyApiService
import com.example.userlist.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        mockyApiService: MockyApiService,
        handleResponse: HandleResponse
    ): UsersRepository {
        return UsersRepositoryImpl(mockyApiService, handleResponse)
    }

//    @Provides
//    @Singleton
//    fun provideCurrentUserRepository(
//        reqresApiService: ReqresApiService,
//        handleResponse: HandleResponse
//    ): CurrentUserRepository {
//        return CurrentUserRepositoryImpl(reqresApiService, handleResponse)
//    }

}