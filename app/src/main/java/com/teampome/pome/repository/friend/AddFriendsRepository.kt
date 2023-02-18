package com.teampome.pome.repository.friend

import javax.inject.Inject

class AddFriendsRepository @Inject constructor(
    private val dataSource: AddFriendsDataSource
){
    fun findFriendsData(nickName: String) = dataSource.findFriendsData(nickName)
    fun addFriend(friendId: String) = dataSource.addFriend(friendId)

    fun getFriend() = dataSource.getFriend()

    fun getFriendRecord(userId : String) = dataSource.getFriendRecord(userId)
}