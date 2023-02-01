package com.teampome.pome.repository.record

import android.util.Log
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.RecordTestData
import com.teampome.pome.model.TestAlarmsData
import com.teampome.pome.model.base.BasePomeListResponse
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
    suspend fun getRecordTestData() : RecordTestData? {
        return recordDataSource.getRecordTestData()
    }

    suspend fun getRecordAlarmsTestData() : List<TestAlarmsData> {
        return recordDataSource.getRecordTestAlarmsData()
    }

    fun getRecordDataByUserId(): Flow<ApiResponse<BasePomeListResponse<RecordData>>> {
        val userId = runBlocking {
            userManager.getUserId().first()
        }

        Log.d("userId", "userId is $userId")

        return recordDataSource.getRecordDataByUserId(userId!!)
    }
}