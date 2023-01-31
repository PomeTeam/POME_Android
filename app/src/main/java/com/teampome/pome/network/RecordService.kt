package com.teampome.pome.network

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.request.BasePomeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecordService {

    @GET("api/v1/records/users/{userId}")
    suspend fun getRecordDataByUserId(
        @Path("userId") userId: String
    ) : Response<BasePomeListResponse<RecordData>>
}