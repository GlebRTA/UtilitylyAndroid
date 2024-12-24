package com.gvituskins.vituskinsandroid.data.network.utils

import com.gvituskins.vituskinsandroid.domain.models.common.NetworkResult
import retrofit2.Response

suspend inline fun <T, M> apiCall(mapper: (T) -> M, crossinline callback: suspend () -> Response<T>): NetworkResult<M> {
    return try {
        val response = callback()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            val mappedBody = mapper(body)
            NetworkResult.Success(mappedBody)
        } else {
            NetworkResult.Error("${response.code()} ${response.message()}")
        }
    } catch (exception: Throwable) {
        NetworkResult.Error(exception.message ?: exception.toString())
    }
}
