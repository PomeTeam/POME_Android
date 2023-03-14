package com.teampome.pome.repository.record

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.teampome.pome.model.RecordData
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
    fun getRecordPagingData(goalId: Int, pagingConfig: PagingConfig): Flow<PagingData<RecordData>>
    fun deleteRecordByRecordId(recordId: Int): Flow<ApiResponse<BasePomeResponse<Any>>>
}