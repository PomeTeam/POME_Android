package com.teampome.pome.repository.record

import com.teampome.pome.model.RecordTestData

interface RecordDataSource {
    suspend fun getRecordTestData(): RecordTestData?
}