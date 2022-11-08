package com.teampome.pome.repository

import com.teampome.pome.model.AddFriendsTestData

interface AddFriendsDataSource {
    suspend fun getFriendsData() : List<AddFriendsTestData>
}