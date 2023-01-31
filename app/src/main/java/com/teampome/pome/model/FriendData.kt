package com.teampome.pome.model

data class FriendData(
    val friend: String,
    val friendNickName: String,
    val friendUserId: String,
    val imageKey: String,
    val isAdd: Boolean? = false
)