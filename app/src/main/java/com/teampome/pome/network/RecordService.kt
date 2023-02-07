package com.teampome.pome.network

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.ConsumeRecordDataBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecordService {

    @GET("api/v1/records/users/{userId}")
    suspend fun getRecordDataByUserId(
        @Path("userId") userId: String
    ) : Response<BasePomeResponse<List<RecordData>>>

    /**
     *  소비 기록 작성
     */
    @POST("api/v1/records")
    suspend fun writeConsumeRecord(
        @Body consumeRecordDataBody: ConsumeRecordDataBody
    ) : Response<BasePomeResponse<RecordData>>

    /**
     *  기록 페이징 조회
     */
    @GET("api/v1/records/goal/{goalId}")
    suspend fun getRecordByGoalId(
        @Path("goalId") goalId: Int
    ) : Response<BasePomeResponse<BaseAllData<RecordData>>>
}