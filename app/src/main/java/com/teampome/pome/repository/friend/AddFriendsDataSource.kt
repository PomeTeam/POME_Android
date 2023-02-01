package com.teampome.pome.repository.friend

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.friend.FriendData
import com.teampome.pome.model.base.BasePomeListResponse
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AddFriendsDataSource {
    fun findFriendsData(nickName: String) : Flow<ApiResponse<BasePomeListResponse<FriendData>>>
    fun addFriend(friendId: String) : Flow<ApiResponse<BasePomeResponse<Boolean>>>
}