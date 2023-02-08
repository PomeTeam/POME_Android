package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.FriendData
import com.teampome.pome.model.BasePomeListResponse
import com.teampome.pome.model.response.GetFriends
import com.teampome.pome.repository.friend.AddFriendsRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    private val repository: AddFriendsRepository
) : BaseViewModel() {

    val findText = MutableLiveData<String>()

    private val _findFriendsDataResponse = MutableLiveData<ApiResponse<BasePomeListResponse<FriendData>>>()
    val findFriendsDataResponse: LiveData<ApiResponse<BasePomeListResponse<FriendData>>> = _findFriendsDataResponse

    private val _addFriendResponse = MutableLiveData<ApiResponse<BasePomeResponse<Boolean>>>()
    val addFriendResponse: LiveData<ApiResponse<BasePomeResponse<Boolean>>> = _addFriendResponse

    private val _friendsData = MutableLiveData<List<FriendData>?>()
    val friendsData: LiveData<List<FriendData>?> = _friendsData

    //친구 목록 조회
    private val _getFriendResponse = MutableLiveData<ApiResponse<BasePomeResponse<List<GetFriends>>>>()
    val getFriendsResponse : LiveData<ApiResponse<BasePomeResponse<List<GetFriends>>>> = _getFriendResponse

    fun findFriendsData(nickName: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _findFriendsDataResponse,
        coroutineErrorHandler
    ) {
        repository.findFriendsData(nickName)
    }

    fun addFriend(friendId: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _addFriendResponse,
        coroutineErrorHandler
    ) {
        repository.addFriend(friendId)
    }

    fun getFriend(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getFriendResponse,
        coroutineErrorHandler
    ){
        repository.getFriend()
    }

    fun settingFriendsData(item: List<FriendData>?) {
        _friendsData.value = item
    }
}