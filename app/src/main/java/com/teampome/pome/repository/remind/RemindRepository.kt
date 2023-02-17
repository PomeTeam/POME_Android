package com.teampome.pome.repository.remind

import javax.inject.Inject

class RemindRepository @Inject constructor(
    private val dataSource: RemindDataSource
) {
    fun getRemindRecords(
        goalId: Int,
        firstEmotion: Int?,
        secondEmotion: Int?
    ) = dataSource.getRemindRecords(
        goalId, firstEmotion, secondEmotion
    )
}