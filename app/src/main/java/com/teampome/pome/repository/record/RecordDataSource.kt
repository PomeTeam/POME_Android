package com.teampome.pome.repository.record

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.RecordTestData
import com.teampome.pome.model.TestAlarmsData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.ConsumeRecordDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RecordDataSource {
    suspend fun getRecordTestData(): RecordTestData?
    suspend fun getRecordTestAlarmsData(): List<TestAlarmsData>
    fun getRecordDataByUserId(userId: String): Flow<ApiResponse<BasePomeResponse<List<RecordData>>>>
    fun writeConsumeRecord(consumeRecordDataBody: ConsumeRecordDataBody): Flow<ApiResponse<BasePomeResponse<RecordData>>>
}