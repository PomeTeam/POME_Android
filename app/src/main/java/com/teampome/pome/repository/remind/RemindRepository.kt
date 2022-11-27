package com.teampome.pome.repository.remind

import com.teampome.pome.model.RemindTestData
import javax.inject.Inject

class RemindRepository @Inject constructor(
    private val dataSource: RemindDataSource
) {
    suspend fun getTestRemindData() : List<RemindTestData>? {
        return dataSource.getTestRemindData()
    }
}