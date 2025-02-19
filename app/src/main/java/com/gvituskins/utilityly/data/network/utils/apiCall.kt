package com.gvituskins.utilityly.data.network.utils

import com.gvituskins.utilityly.domain.models.common.Either
import com.gvituskins.utilityly.domain.models.common.EitherNetwork
import retrofit2.Response

suspend inline fun <T, M> apiCall(
    mapper: (T) -> M,
    crossinline callback: suspend () -> Response<T>
): EitherNetwork<M> {
    return try {
        val response = callback()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            val mappedBody = mapper(body)
            Either.Success(mappedBody)
        } else {
            Either.Error("${response.code()} ${response.message()}")
        }
    } catch (exception: Throwable) {
        Either.Error(exception.message ?: exception.toString())
    }
}
