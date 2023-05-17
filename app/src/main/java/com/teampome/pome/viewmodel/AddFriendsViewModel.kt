package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.friend.FriendData
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.response.DeleteFriend
import com.teampome.pome.model.response.GetFriendRecord
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

    private val _findFriendsDataResponse = MutableLiveData<ApiResponse<BasePomeResponse<List<FriendData>>>>()
    val findFriendsDataResponse: LiveData<ApiResponse<BasePomeResponse<List<FriendData>>>> = _findFriendsDataResponse

    private val _addFriendResponse = MutableLiveData<ApiResponse<BasePomeResponse<Boolean>>>()
    val addFriendResponse: LiveData<ApiResponse<BasePomeResponse<Boolean>>> = _addFriendResponse

    private val _friendsData = MutableLiveData<List<FriendData>?>()
    val friendsData: LiveData<List<FriendData>?> = _friendsData

    //친구 목록 조회
    private val _getFriendResponse = MutableLiveData<ApiResponse<BasePomeResponse<List<GetFriends>>>>()
    val getFriendsResponse : LiveData<ApiResponse<BasePomeResponse<List<GetFriends>>>> = _getFriendResponse

    //친구 기록 조회
    private val _getFriendRecordResponse = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<GetFriendRecord>>>>()
    val getFriendRecordResponse : LiveData<ApiResponse<BasePomeResponse<BaseAllData<GetFriendRecord>>>> = _getFriendRecordResponse

    //친구 삭제
    private val _deleteFriendResponse = MutableLiveData<ApiResponse<BasePomeResponse<DeleteFriend>>>()
    val deleteFriendResponse : LiveData<ApiResponse<BasePomeResponse<DeleteFriend>>> = _deleteFriendResponse

    //모든 친구 기록 조회
    private val _getAllFriendRecordResponse = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<GetFriendRecord>>>>()
    val getAllFriendRecordResponse : LiveData<ApiResponse<BasePomeResponse<BaseAllData<GetFriendRecord>>>> = _getAllFriendRecordResponse


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

    //친구 목록 조회
    fun getFriend(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getFriendResponse,
        coroutineErrorHandler
    ){
        repository.getFriend()
    }

    fun settingFriendsData(item: List<FriendData>?) {
        _friendsData.value = item
    }

    //친구 기록 조회
    fun getRecordFriend(userId : String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getFriendRecordResponse,
        coroutineErrorHandler
    ){
        repository.getFriendRecord(userId)
    }

    //친구 삭제
    fun deleteFriend(friendId : String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _deleteFriendResponse,
        coroutineErrorHandler
    ){
        repository.deleteFriend(friendId)
    }

    fun getAllRecordFriend(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getAllFriendRecordResponse,
        coroutineErrorHandler
    ){
        repository.getAllFriendRecord()
    }

    //친구 조회
    val friendGet: LiveData<List<GetFriends?>> = Transformations.map(_getFriendResponse) {
        when(it) {
            is ApiResponse.Success -> {
                it.data.data?:run{null}
            }
            is ApiResponse.Loading -> { null }
            is ApiResponse.Failure -> { null }
        }
    }


}