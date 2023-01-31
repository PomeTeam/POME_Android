package com.teampome.pome.repository.friend

import com.teampome.pome.model.FriendData
import com.teampome.pome.model.request.BasePomeListResponse
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AddFriendsDataSource {
    fun findFriendsData(nickName: String) : Flow<ApiResponse<BasePomeListResponse<FriendData>>>
}