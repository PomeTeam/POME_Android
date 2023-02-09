package com.teampome.pome.network

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.friend.FriendData
import com.teampome.pome.model.response.GetFriends
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
    ) : Response<BasePomeResponse<List<FriendData>>>

    @POST("api/v1/users/friend/{friendId}")
    suspend fun addFriend(
        @Path("friendId") friendId: String
    ) : Response<BasePomeResponse<Boolean>>

    //친구 목록 조회
    @GET("api/v1/users/friends")
    suspend fun getFriend()
        : Response<BasePomeResponse<List<GetFriends>>>
}