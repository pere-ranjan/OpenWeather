package com.ranjan.openweather.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

sealed class ResponseHelper<out T>(
    val data: T? = null, val responseCode: Int? = null, val message: String? = null
) {
    class Success<T>(data: T, responseCode: Int?) :
        ResponseHelper<T>(data = data, responseCode = responseCode)

    class Loading : ResponseHelper<Nothing>()
    class Error(message: String?, responseCode: Int? = null) :
        ResponseHelper<Nothing>(responseCode = responseCode, message = message)
}

fun <T> safeApiCall(
    networkUtils: NetworkUtils, apiToCall: suspend () -> Response<T>
): Flow<ResponseHelper<T>> = flow {
    emit(ResponseHelper.Loading())
    try {
        if (networkUtils.hasNetworkConnection()) {
            val response = apiToCall.invoke()
            if (response.isSuccessful) emit(
                ResponseHelper.Success(
                    data = response.body()!!, responseCode = response.code()
                )
            )
            else emit(
                ResponseHelper.Error(
                    message = response.message(), responseCode = response.code()
                )
            )
        } else {
            emit(ResponseHelper.Error("No Internet Connection"))
        }
    } catch (e: IOException) {
        emit(ResponseHelper.Error("No Internet Connection"))
    } catch (e: Exception) {
        emit(ResponseHelper.Error(message = e.message ?: "Something went wrong"))
    }
}.flowOn(Dispatchers.IO)
