package com.gvituskins.vituskinsandroid.domain.models.common

sealed interface NetworkResult<T> {
    class Success<T>(val data: T) : NetworkResult<T>
    class Error<T>(val message: String) : NetworkResult<T>
}
