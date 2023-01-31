package com.teampome.pome.util.token

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ImageUrlInterceptor @Inject constructor(
    private val userManager: UserManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val baseRequest = chain.request()
        val newRequest = chain.request().newBuilder()

        val preSignedUrl = runBlocking {
            userManager.getUserProfileUrl().first()
        }

        Log.d("presignedUrl", "check url : $preSignedUrl")

        return preSignedUrl?.let {
            newRequest.url(it)

            Log.d("presignedUrl", "send new request ${chain.proceed(newRequest.build())}")

            chain.proceed(newRequest.build())
        } ?: run {
            chain.proceed(baseRequest)
        }
    }
}