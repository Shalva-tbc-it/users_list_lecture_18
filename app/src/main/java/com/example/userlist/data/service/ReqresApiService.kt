package com.example.userlist.data.service

import com.example.userlist.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReqresApiService {
    @GET("/api/users/{id}")
    suspend fun getCurrentUser(@Path("id") id: Int): Response<UserResponse>

}