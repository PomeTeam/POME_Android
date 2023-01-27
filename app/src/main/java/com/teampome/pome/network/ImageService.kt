package com.teampome.pome.network

import com.teampome.pome.model.PresignedUrlImageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("presigned-url")
    suspend fun getPresignedUrl(
        @Query("id") id: String
    ) : Response<PresignedUrlImageData>
}