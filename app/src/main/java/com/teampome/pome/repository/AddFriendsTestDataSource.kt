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
            ),
            AddFriendsTestData(
                "윤성식",
                ""
            ),
            AddFriendsTestData(
                "고민채",
                ""
            ),
            AddFriendsTestData(
                "박소윤",
                ""
            ),
            AddFriendsTestData(
                "강효진",
                ""
            ),
            AddFriendsTestData(
                "서연진",
                ""
            ),
            AddFriendsTestData(
                "선아",
                ""
            ),
            AddFriendsTestData(
                "양은진",
                ""
            ),
            AddFriendsTestData(
                "은조미",
                ""
            ),
            AddFriendsTestData(
                "이찬영",
                ""
            ),
            AddFriendsTestData(
                "한규범",
                ""
            )
        )
    }
}