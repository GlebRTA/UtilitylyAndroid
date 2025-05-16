package com.gvituskins.utilityly.domain.models.common

typealias EitherNetwork <T> = Either<T, String>
typealias EitherEmpty = Either<Unit, String>

sealed interface Either<out SUCCESS, out ERROR> {
    data class Success<SUCCESS>(val value: SUCCESS) : Either<SUCCESS, Nothing>
    data class Error<ERROR>(val value: ERROR) : Either<Nothing, ERROR>
}
