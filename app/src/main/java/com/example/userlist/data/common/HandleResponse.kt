package com.example.userlist.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

class HandleResponse {
    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<List<T>>): Flow<Resource<List<T>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: emptyList()))
            } else {
                emit(Resource.Error("Error Code: ${response.code()}"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error: $e"))
        } catch (e: Throwable) {
            emit(Resource.Error("Unknown error: $e"))
        }
    }

    suspend fun <T : Any> apiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { emit(Resource.Success(it)) }
                    ?: "Empty response body"
            } else {
                emit(Resource.Error("Error Code: ${response.code()}"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error: $e"))
        } catch (e: Throwable) {
            emit(Resource.Error("Unknown error: $e"))
        }
    }


}