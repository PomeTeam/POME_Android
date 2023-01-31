package com.teampome.pome.repository.friend

import com.teampome.pome.model.FriendData
import com.teampome.pome.model.request.BasePomeListResponse
import com.teampome.pome.network.AddFriendsService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFriendsRemoteDataSource @Inject constructor(
    private val service: AddFriendsService
) : AddFriendsDataSource {
    override fun findFriendsData(nickName: String) : Flow<ApiResponse<BasePomeListResponse<FriendData>>> = apiRequestFlow {
        service.findFriendsData(nickName)
    }
}