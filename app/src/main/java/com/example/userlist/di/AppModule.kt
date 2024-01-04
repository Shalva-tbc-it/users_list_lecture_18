package com.example.userlist.di

import com.example.userlist.data.service.MockyApiService
import com.example.userlist.domain.repository.UsersRepository
import com.example.userlist.domain.usecase.GetUsersUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder().build()
//    }

    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .baseUrl("https://run.mocky.io")
            .build()
    }

//    @Provides
//    @Singleton
//    fun retrofit(moshi: Moshi) : Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://run.mocky.io")
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//            .create(MockyApiService::class.java)
//    }

    @Provides
    @Singleton
    fun provideMockyApiService(retrofit: Retrofit): MockyApiService {
        return retrofit.create(MockyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(usersRepository: UsersRepository): GetUsersUseCase {
        return GetUsersUseCase(repository = usersRepository)
    }



}
