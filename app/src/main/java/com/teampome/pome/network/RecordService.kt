package com.teampome.pome.network

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BasePomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordService {

    @GET("api/v1/records/users/{userId}")
    suspend fun getRecordDataByUserId(
        @Path("userId") userId: String
    ) : Response<BasePomeResponse<List<RecordData>>>
}