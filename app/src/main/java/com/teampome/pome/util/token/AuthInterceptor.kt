package com.teampome.pome.util.token

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken().first() // 항상 첫번째 element만
        }

        // token을 가져와서 넣어주는 작업
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "$token")
        return chain.proceed(request.build())
    }
}