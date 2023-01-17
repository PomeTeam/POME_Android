package com.teampome.pome.repository.record

import com.teampome.pome.model.RecordTestData
import com.teampome.pome.model.TestAlarmsData

interface RecordDataSource {
    suspend fun getRecordTestData(): RecordTestData?

    suspend fun getRecordTestAlarmsData(): List<TestAlarmsData>
}