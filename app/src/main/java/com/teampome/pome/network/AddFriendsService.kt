package com.teampome.pome.network

import com.teampome.pome.model.FriendData
import com.teampome.pome.model.request.BasePomeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AddFriendsService {
    @GET("api/v1/users/friend/{friendId}")
    suspend fun findFriendsData(
        @Path("friendId") friendId: String
    ) : Response<BasePomeListResponse<FriendData>>
}