package com.teampome.pome.network

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

/**
 *  ImageService를 이용하지 않은 것은 다른 retrofit 객체를 이용해야하기 때문 : (ImageInterceptor 추가)
 */
interface PreSignedImageService {

    @Multipart
    @PUT(".")
    fun sendImage(
        @Part file: MultipartBody.Part
    ) : Response<Void>
}