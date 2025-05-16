package com.gvituskins.utilityly.presentation.core.utils

import com.gvituskins.utilityly.domain.models.common.Either
import com.gvituskins.utilityly.presentation.components.snackbar.SnackbarController
import com.gvituskins.utilityly.presentation.components.snackbar.SnackbarEvent

suspend fun handleSnackbarDbCall(
    result: Either<Unit, String>,
    successMessage: String?,
) {

    when (result) {
        is Either.Success -> {
            successMessage?.let {
                SnackbarController.sendEvent(SnackbarEvent(message = successMessage))
            }
        }
        is Either.Error -> {
            SnackbarController.sendEvent(SnackbarEvent(message = "Error: ${result.value}"))
        }
    }
}
