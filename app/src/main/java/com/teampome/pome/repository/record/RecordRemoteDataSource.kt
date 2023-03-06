package com.teampome.pome.repository.record

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.teampome.pome.model.*
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.ConsumeRecordDataBody
import com.teampome.pome.model.request.EmotionDataBody
import com.teampome.pome.network.RecordService
import com.teampome.pome.repository.record.paging.RecordPagingSource
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

    override fun writeSecondEmotion(
        recordId: Int,
        emotionDataBody: EmotionDataBody
    ): Flow<ApiResponse<BasePomeResponse<RecordData>>> = apiRequestFlow {
        service.writeSecondEmotion(recordId, emotionDataBody)
    }

    override fun updateRecord(
        recordId: Int,
        recordDataBody: ConsumeRecordDataBody
    ): Flow<ApiResponse<BasePomeResponse<RecordData>>> = apiRequestFlow {
        service.updateRecord(recordId, recordDataBody)
    }

    override fun getRecordByGoalId(goalId: Int): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = apiRequestFlow {
        service.getRecordByGoalId(goalId, null, null)
    }

    override fun getOneWeekGoalByGoalId(goalId: Int): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = apiRequestFlow {
        service.getOneWeekGoalByGoalId(goalId)
    }

    override fun getRecordPagingData(goalId: Int): Flow<PagingData<RecordData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                prefetchDistance = 3,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                RecordPagingSource(service = service, goalId = goalId)
            }
        ).flow
    }
}