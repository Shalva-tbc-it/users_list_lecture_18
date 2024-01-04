package com.example.userlist.data.service

import com.example.userlist.data.model.UsersDTO
import retrofit2.Response
import retrofit2.http.GET

interface MockyApiService {
    @GET("/v3/7ec14eae-06bf-4f6d-86d2-ac1b9df5fe3d")
    suspend fun getUsersDTO(): Response<List<UsersDTO>>
}