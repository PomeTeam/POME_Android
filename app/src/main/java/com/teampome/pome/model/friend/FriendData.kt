package com.teampome.pome.model.friend

data class FriendData(
    val friend: String,
    val friendNickName: String,
    val friendUserId: String,
    val imageKey: String,
    var isAdd: Boolean? = false
)