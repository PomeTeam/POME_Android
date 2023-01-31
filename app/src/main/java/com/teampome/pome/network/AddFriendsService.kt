package com.teampome.pome.network

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.FriendData
import com.teampome.pome.model.request.BasePomeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AddFriendsService {
    /**
     *  friendId를 이용하여 친구찾기
     */
    @GET("api/v1/users/friend/{friendId}")
    suspend fun findFriendsData(
        @Path("friendId") friendId: String
    ) : Response<BasePomeListResponse<FriendData>>

    @POST("api/v1/users/friend/{friendId}")
    suspend fun addFriend(
        @Path("friendId") friendId: String
    ) : Response<BasePomeResponse<Boolean>>
}