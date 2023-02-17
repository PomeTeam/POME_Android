package com.teampome.pome.repository.remind

import com.teampome.pome.model.*
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.network.RemindService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemindTestDataSource @Inject constructor(
    private val remindService: RemindService
) : RemindDataSource {

    override fun getRemindRecords(
        goalId: Int,
        firstEmotion: Int?,
        secondEmotion: Int?
    ): Flow<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = apiRequestFlow {
        remindService.getRemindRecords(
            goalId, firstEmotion, secondEmotion
        )
    }
}