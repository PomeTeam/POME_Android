package com.teampome.pome.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.response.GetFriends
import com.teampome.pome.repository.friend.AddFriendsRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageFriendViewModel @Inject constructor(
    private val repository: AddFriendsRepository
): BaseViewModel() {

    //친구 목록 조회
    private val _getFriendResponse = MutableLiveData<ApiResponse<BasePomeResponse<List<GetFriends>>>>()
    val getFriendsResponse : LiveData<ApiResponse<BasePomeResponse<List<GetFriends>>>> = _getFriendResponse

    //친구 삭제
    private val _deleteFriendResponse = MutableLiveData<ApiResponse<BasePomeResponse<Boolean>>>()
    val deleteFriendResponse : LiveData<ApiResponse<BasePomeResponse<Boolean>>> = _deleteFriendResponse

    //친구 목록 조회
    fun getFriend(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getFriendResponse,
        coroutineErrorHandler
    ){
        repository.getFriend()
    }

    // 친구 삭제
    fun deleteFriend(friendId : String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _deleteFriendResponse,
        coroutineErrorHandler
    ){
        repository.deleteFriend(friendId)
    }
}