package com.teampome.pome.repository.record

import com.teampome.pome.model.RecordTestData
import com.teampome.pome.model.TestAlarmsData
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDataSource: RecordDataSource
) {
    suspend fun getRecordTestData() : RecordTestData? {
        return recordDataSource.getRecordTestData()
    }

    suspend fun getRecordAlarmsTestData() : List<TestAlarmsData> {
        return recordDataSource.getRecordTestAlarmsData()
    }
}