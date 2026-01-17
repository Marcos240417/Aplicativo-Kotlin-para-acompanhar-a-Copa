package com.example.mycopa.data.remote.extensions

import com.example.mycopa.data.remote.NotFoundException
import com.example.mycopa.data.remote.UnexpectedException
import java.net.HttpURLConnection
import retrofit2.HttpException as RetrofitHttpException

// Esta função será usada no Repositório para converter Result em erro de domínio
internal fun <T> Result<T>.getOrThrowDomainError(): T = getOrElse { throwable ->
    throw throwable.toDomainError()
}

internal fun Throwable.toDomainError(): Throwable {
    return when (this) {
        is RetrofitHttpException -> {
            when (code()) {
                HttpURLConnection.HTTP_NOT_FOUND ->
                    NotFoundException("Ops! Não conseguimos encontrar as partidas :'(")
                else -> UnexpectedException()
            }
        }
        else -> this
    }
}