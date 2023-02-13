package com.teampome.pome.network

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.ConsumeRecordDataBody
import com.teampome.pome.model.request.EmotionDataBody
import retrofit2.Response
import retrofit2.http.*

interface RecordService {

    /**
     *  userId를 통해 기록들 가져오기
     */
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
     *  두번째 감정 기록 작성
     */
    @POST("api/v1/records/{recordId}/second-emotion")
    suspend fun writeSecondEmotion(
        @Path("recordId") recordId: Int,
        @Body emotionDataBody: EmotionDataBody
    ) : Response<BasePomeResponse<RecordData>>

    /**
     *  소비 기록 수정
     */
    @PUT("api/v1/records/{recordId}")
    suspend fun updateRecord(
        @Path("recordId") recordId: Int,
        @Body recordDataBody: ConsumeRecordDataBody
    ) : Response<BasePomeResponse<RecordData>>

    /**
     *  기록 페이징 조회
     */
    @GET("api/v1/records/goal/{goalId}/record-tab")
    suspend fun getRecordByGoalId(
        @Path("goalId") goalId: Int
    ) : Response<BasePomeResponse<BaseAllData<RecordData>>>

    /**
     *  일주일이 지난 기록 조회
     */
    @GET("api/v1/records/one-week/goal/{goalId}")
    suspend fun getOneWeekGoalByGoalId(
        @Path("goalId") goalId: Int
    ) : Response<BasePomeResponse<BaseAllData<RecordData>>>
}