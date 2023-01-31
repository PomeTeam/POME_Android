package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.FriendData
import com.teampome.pome.model.request.BasePomeListResponse
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

    private val _findFriendsDataResponse = MutableLiveData<ApiResponse<BasePomeListResponse<FriendData>>>()
    val findFriendsDataResponse: LiveData<ApiResponse<BasePomeListResponse<FriendData>>> = _findFriendsDataResponse

    fun findFriendsData(nickName: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _findFriendsDataResponse,
        coroutineErrorHandler
    ) {
        repository.findFriendsData(nickName)
    }
}