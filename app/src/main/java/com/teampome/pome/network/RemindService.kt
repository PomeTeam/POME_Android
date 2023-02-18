package com.teampome.pome.network

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemindService {
    /**
     *  회고탭 조회
     */
    @GET("/api/v1/records/goal/{goalId}/retrospection-tab")
    suspend fun getRemindRecords(
        @Path("goalId") goalId: Int,
        @Query("first_emotion") firstEmotion: Int?,
        @Query("second_emotion") secondEmotion: Int?
    ) : Response<BasePomeResponse<BaseAllData<RecordData>>>
}