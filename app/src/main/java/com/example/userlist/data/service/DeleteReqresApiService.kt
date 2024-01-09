package com.example.userlist.data.service

import com.example.userlist.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteReqresApiService {
    @DELETE("/api/users/{id}")
    suspend fun deleteCurrentUser(@Path("id") id: Int): Response<UserResponse>
}