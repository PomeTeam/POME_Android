package com.teampome.pome.network

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

/**
 *  ImageService를 이용하지 않은 것은 다른 retrofit 객체를 이용해야하기 때문 : (ImageInterceptor 추가)
 */
interface PreSignedImageService {

    @PUT(".")
    suspend fun sendImage(
        @Body body: RequestBody
    ) : Response<Void>
}