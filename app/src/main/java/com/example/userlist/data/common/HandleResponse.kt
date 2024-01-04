package com.example.userlist.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

class HandleResponse {
    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<List<T>>): Flow<Resource<List<T>>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: emptyList()))
                emit(Resource.Loading(false))
            } else {
                emit(Resource.Error("Error Code: ${response.code()}"))
                emit(Resource.Loading(false))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error: $e"))
            emit(Resource.Loading(false))
        } catch (e: Throwable) {
            emit(Resource.Error("Unknown error: $e"))
            emit(Resource.Loading(false))
        }
    }
}