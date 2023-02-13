package com.teampome.pome.repository.record

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.RecordTestData
import com.teampome.pome.model.TestAlarmsData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.ConsumeRecordDataBody
import com.teampome.pome.model.request.EmotionDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RecordDataSource {
    fun getRecordDataByUserId(userId: String): Flow<ApiResponse<BasePomeResponse<List<RecordData>>>>
    fun writeConsumeRecord(consumeRecordDataBody: ConsumeRecordDataBody): Flow<ApiResponse<BasePomeResponse<RecordData>>>
    fun writeSecondEmotion(recordId: Int, emotionDataBody: EmotionDataBody): Flow<ApiResponse<BasePomeResponse<RecordData>>>
    fun updateRecord(recordId: Int, recordDataBody: ConsumeRecordDataBody): Flow<ApiResponse<BasePomeResponse<RecordData>>>
    fun getRecordByGoalId(goalId: Int): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>
    fun getOneWeekGoalByGoalId(goalId: Int): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>
}