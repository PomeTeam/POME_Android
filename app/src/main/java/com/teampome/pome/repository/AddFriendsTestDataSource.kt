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
                null
            ),
            AddFriendsTestData(
                "윤성식",
                null
            ),
            AddFriendsTestData(
                "고민채",
                null
            ),
            AddFriendsTestData(
                "박소윤",
                null
            ),
            AddFriendsTestData(
                "강효진",
                null
            ),
            AddFriendsTestData(
                "서연진",
                null
            ),
            AddFriendsTestData(
                "선아",
                null
            ),
            AddFriendsTestData(
                "양은진",
                null
            ),
            AddFriendsTestData(
                "은조미",
                null
            ),
            AddFriendsTestData(
                "이찬영",
                null
            ),
            AddFriendsTestData(
                "한규범",
                null
            )
        )
    }
}