package com.teampome.pome.repository.friend

import com.teampome.pome.model.AddFriendsTestData
import javax.inject.Inject

class AddFriendsRepository @Inject constructor(
    private val dataSource: AddFriendsDataSource
){
    suspend fun getFriendsTestData() : List<AddFriendsTestData> {
        return dataSource.getFriendsData()
    }
}