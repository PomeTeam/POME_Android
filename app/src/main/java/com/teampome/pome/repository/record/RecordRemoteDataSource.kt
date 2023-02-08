package com.teampome.pome.repository.record

import com.teampome.pome.model.*
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.ConsumeRecordDataBody
import com.teampome.pome.network.RecordService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRemoteDataSource @Inject constructor(
    private val service: RecordService
): RecordDataSource {
    override fun getRecordDataByUserId(userId: String): Flow<ApiResponse<BasePomeResponse<List<RecordData>>>> = apiRequestFlow {
        service.getRecordDataByUserId(userId)
    }

    override fun writeConsumeRecord(consumeRecordDataBody: ConsumeRecordDataBody): Flow<ApiResponse<BasePomeResponse<RecordData>>> = apiRequestFlow {
        service.writeConsumeRecord(consumeRecordDataBody)
    }

    override fun getRecordByGoalId(goalId: Int): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = apiRequestFlow {
        service.getRecordByGoalId(goalId)
    }

    override fun getOneWeekGoalByGoalId(goalId: Int): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = apiRequestFlow {
        service.getOneWeekGoalByGoalId(goalId)
    }
}