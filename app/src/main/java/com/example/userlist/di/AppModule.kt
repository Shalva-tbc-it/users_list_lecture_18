package com.example.userlist.di

import com.example.userlist.data.service.MockyApiService
import com.example.userlist.data.service.ReqresApiService
import com.example.userlist.domain.repository.CurrentUserRepository
import com.example.userlist.domain.repository.UsersRepository
import com.example.userlist.domain.usecase.GetCurrentUserUseCase
import com.example.userlist.domain.usecase.GetUsersUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL_MOCKY = "https://run.mocky.io"
    private const val BASE_URL_REQRES = "https://reqres.in"

    @Provides
    @Singleton
    @Named("RetrofitMocky")
    fun retrofitMocky(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .baseUrl(BASE_URL_MOCKY)
            .build()
    }

    @Provides
    @Singleton
    fun provideMockyApiService(@Named("RetrofitMocky") retrofit: Retrofit): MockyApiService {
        return retrofit.create(MockyApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("RetrofitReqres")
    fun retrofitReqres(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .baseUrl(BASE_URL_REQRES)
            .build()
    }

    @Provides
    @Singleton
    fun provideReqresApiService(@Named("RetrofitReqres") retrofit: Retrofit) : ReqresApiService {
        return retrofit.create(ReqresApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideGetUsersUseCase(usersRepository: UsersRepository): GetUsersUseCase {
        return GetUsersUseCase(repository = usersRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(currentUserRepository: CurrentUserRepository) : GetCurrentUserUseCase {
        return GetCurrentUserUseCase(repository = currentUserRepository)
    }

}
