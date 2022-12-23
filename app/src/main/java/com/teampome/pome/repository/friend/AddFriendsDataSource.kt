package com.teampome.pome.repository.friend

import com.teampome.pome.model.AddFriendsTestData

interface AddFriendsDataSource {
    suspend fun getFriendsData() : List<AddFriendsTestData>
}