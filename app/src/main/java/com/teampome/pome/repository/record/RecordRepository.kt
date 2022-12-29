package com.teampome.pome.repository.record

import com.teampome.pome.model.RecordTestData
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDataSource: RecordDataSource
) {
    suspend fun getRecordTestData() : RecordTestData? {
        return recordDataSource.getRecordTestData()
    }
}