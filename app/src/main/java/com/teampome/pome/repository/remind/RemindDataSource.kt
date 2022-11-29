package com.teampome.pome.repository.remind

import com.teampome.pome.model.RemindTestData

interface RemindDataSource {
    suspend fun getTestRemindData() : RemindTestData?
}