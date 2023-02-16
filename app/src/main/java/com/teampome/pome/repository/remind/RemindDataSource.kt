package com.teampome.pome.repository.remind

import com.teampome.pome.model.RecordData
import com.teampome.pome.model.RemindTestData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RemindDataSource {
    suspend fun getTestRemindData() : RemindTestData?
    fun getRemindRecords(goalId: Int, firstEmotion: Int?, secondEmotion: Int?): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>
}