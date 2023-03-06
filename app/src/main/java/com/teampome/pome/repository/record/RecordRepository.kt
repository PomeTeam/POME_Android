package com.teampome.pome.repository.record

import androidx.paging.PagingData
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.ConsumeRecordDataBody
import com.teampome.pome.model.request.EmotionDataBody
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.token.UserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDataSource: RecordDataSource,
    private val userManager: UserManager
) {

    fun getRecordPagingData(goalId: Int): Flow<PagingData<RecordData>> {
        return recordDataSource.getRecordPagingData(goalId)
    }

    fun getRecordDataByUserId(): Flow<ApiResponse<BasePomeResponse<List<RecordData>>>> {
        val userId = runBlocking {
            userManager.getUserId().first()
        }

        return recordDataSource.getRecordDataByUserId(userId!!)
    }

    fun writeConsumeRecord(
        emotionId: Int,
        goalId: Int,
        useComment: String,
        useDate: String,
        usePrice: Long
    ) = recordDataSource.writeConsumeRecord(
        ConsumeRecordDataBody(
            emotionId,
            goalId,
            useComment,
            useDate,
            usePrice
        )
    )

    fun writeSecondEmotion(
        recordId: Int,
        emotionId: Int
    ) = recordDataSource.writeSecondEmotion(
        recordId,
        EmotionDataBody(
            emotionId
        )
    )

    fun updateRecord(
        recordId: Int,
        goalId: Int,
        useComment: String,
        useDate: String,
        usePrice: Long
    ) = recordDataSource.updateRecord(
        recordId,
        ConsumeRecordDataBody(
            emotionId = null,
            goalId = goalId,
            useComment = useComment,
            useDate = useDate,
            usePrice = usePrice
        )
    )

    fun getRecordByGoalId(goalId: Int) = recordDataSource.getRecordByGoalId(goalId)

    fun getOneWeekGoalByGoalId(goalId: Int) = recordDataSource.getOneWeekGoalByGoalId(goalId)
}