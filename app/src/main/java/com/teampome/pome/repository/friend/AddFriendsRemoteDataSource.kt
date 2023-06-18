package com.teampome.pome.repository.friend

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.friend.FriendData
import com.teampome.pome.model.response.DeleteFriend
import com.teampome.pome.model.response.DeleteFriendRecord
import com.teampome.pome.model.response.GetFriendRecord
import com.teampome.pome.model.response.GetFriends
import com.teampome.pome.network.AddFriendsService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFriendsRemoteDataSource @Inject constructor(
    private val service: AddFriendsService
) : AddFriendsDataSource {
    override fun findFriendsData(nickName: String) : Flow<ApiResponse<BasePomeResponse<List<FriendData>>>> = apiRequestFlow {
        service.findFriendsData(nickName)
    }

    override fun addFriend(friendId: String): Flow<ApiResponse<BasePomeResponse<Boolean>>> = apiRequestFlow {
        service.addFriend(friendId)
    }

    override fun getFriend(): Flow<ApiResponse<BasePomeResponse<List<GetFriends>>>> = apiRequestFlow{
        service.getFriend()
    }

    override fun getFriendRecord(userId : String): Flow<ApiResponse<BasePomeResponse<BaseAllData<GetFriendRecord>>>> = apiRequestFlow{
        service.getFriendRecord(userId)
    }

    override fun deleteFriend(friendId: String): Flow<ApiResponse<BasePomeResponse<DeleteFriend>>> = apiRequestFlow{
        service.deleteFriend(friendId)
    }

    override fun getAllFriendRecord(): Flow<ApiResponse<BasePomeResponse<BaseAllData<GetFriendRecord>>>> = apiRequestFlow {
        service.getAllFriendRecord()
    }

    override fun deleteFriendRecord(recordId : Int): Flow<ApiResponse<BasePomeResponse<DeleteFriendRecord>>> = apiRequestFlow {
        service.deleteFriendRecord(recordId)
    }
}