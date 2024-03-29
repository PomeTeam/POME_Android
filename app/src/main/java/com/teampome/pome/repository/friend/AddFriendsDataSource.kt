package com.teampome.pome.repository.friend

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.friend.FriendData
import com.teampome.pome.model.response.GetFriendRecord
import com.teampome.pome.model.response.GetFriends
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AddFriendsDataSource {
    fun findFriendsData(nickName: String) : Flow<ApiResponse<BasePomeResponse<List<FriendData>>>>
    fun addFriend(friendId: String) : Flow<ApiResponse<BasePomeResponse<Boolean>>>

    //친구 목록 조회
    fun getFriend() : Flow<ApiResponse<BasePomeResponse<List<GetFriends>>>>

    //친구 기록 조회
    fun getFriendRecord(
        userId : String
    ) : Flow<ApiResponse<BasePomeResponse<BaseAllData<GetFriendRecord>>>>

    fun deleteFriend(
        friendId: String
    ) : Flow<ApiResponse<BasePomeResponse<Boolean>>>
}