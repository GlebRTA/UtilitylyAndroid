package com.gvituskins.utilityly.data.utils

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.gvituskins.utilityly.domain.models.common.Either

suspend inline fun <T> dbCall(crossinline call: suspend () -> T): Either<T, String> {
    return try {
        val result = call()
        Either.Success(result)
    } catch (e: Exception) {
        Firebase.crashlytics.recordException(e)
        Either.Error(e.message ?: e.toString())
    }
}
