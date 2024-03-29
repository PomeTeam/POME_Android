package com.teampome.pome.util.base

import android.util.Log
import com.google.gson.Gson
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.base.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

/**
 *   api 기본 요청 메소드
 *   Call을 통해 요청하면 Flow로 감싸 ApiResponse결과를 내어준다.
 *   ApiResponse.Success, ApiResponse.Failure, ApiResponse.Loading 3가지 상태로 리턴
 *   또한, 2초이상 요청이 길어질 경우 자동으로 TimeOut된다.
 *
 *   error code 204 : empty list로 떨어지는 경우
 */

fun<T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    // 먼저, Loading 상태 표현
    emit(ApiResponse.Loading)

    // 20초 TimeOut
    withTimeoutOrNull(30000L) {
        val response = call()

        Log.d("call", "call in $response")

        try {
            if(response.body() is BasePomeResponse<*>) {
                if(response.isSuccessful && (response.body() as BasePomeResponse<*>).success) {
                    response.body()?.let { data ->
                        emit(ApiResponse.Success(data))
                    }
                } else if (!(response.body() as BasePomeResponse<*>).success) {
                    if((response.body() as BasePomeResponse<*>).errorCode == "T0001") { // TokenRefresh 해야하는 부분
                        // 토큰을 변경하고 재시도하는 작업이 필요
                    }

                    emit(ApiResponse.Failure((response.body() as BasePomeResponse<*>).message, (response.body() as BasePomeResponse<*>).errorCode ?: "400"))
                } else {
                    response.errorBody()?.let { error ->
                        error.close()
                        val parsedError: ErrorResponse = Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                        emit(ApiResponse.Failure(parsedError.message[0], parsedError.code.toString()))
                    }
                }
            } else {
                if(response.isSuccessful) {
                    response.body()?.let { data ->
                        emit(ApiResponse.Success(data))
                    }
                } else {
                    response.errorBody()?.let { error ->
                        error.close()
                        val parsedError: ErrorResponse = Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                        emit(ApiResponse.Failure(parsedError.message[0], parsedError.code.toString()))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Failure(e.message ?: e.toString(), "400"))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", "408"))
}.flowOn(Dispatchers.IO)