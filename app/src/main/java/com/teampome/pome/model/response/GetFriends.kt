package com.teampome.pome.model.response

//내 친구 목록 조회 Response
data class GetFriends(
    val friendUserId: String,
    val friendNickName: String,
    val imageKey: String,
    val friend: Boolean
)
