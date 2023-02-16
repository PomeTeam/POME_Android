package com.teampome.pome.repository.remind

import com.teampome.pome.model.RemindTestData
import javax.inject.Inject

class RemindRepository @Inject constructor(
    private val dataSource: RemindDataSource
) {
    suspend fun getTestRemindData() : RemindTestData? {
        return dataSource.getTestRemindData()
    }

    fun getRemindRecords(
        goalId: Int,
        firstEmotion: Int?,
        secondEmotion: Int?
    ) = dataSource.getRemindRecords(
        goalId, firstEmotion, secondEmotion
    )
}