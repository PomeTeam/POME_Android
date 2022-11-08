package com.teampome.pome.repository

import com.teampome.pome.model.AddFriendsTestData
import javax.inject.Inject

class AddFriendsTestDataSource @Inject constructor(

) : AddFriendsDataSource {
    override suspend fun getFriendsData(): List<AddFriendsTestData> {
        // test data 주입
        return listOf(
            AddFriendsTestData(
                "최한슬",
                ""
            )
        )
    }
}