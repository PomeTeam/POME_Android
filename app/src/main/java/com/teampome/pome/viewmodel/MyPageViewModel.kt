package com.teampome.pome.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.mytab.MyTabMarshmello
import com.teampome.pome.repository.mypage.MyPageRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.util.token.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repository: MyPageRepository,
    private val userManager: UserManager
): BaseViewModel() {

    val userName = runBlocking {
        userManager.getUserNickName().first()
    }

    val userProfileUrl = runBlocking {
        userManager.getUserProfileUrl().first()
    }

    init {
        Log.d("userUrl", "user info? : $userName , $userProfileUrl")
    }

    private val _pastGoals = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>>()
    val pastGoals: LiveData<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = _pastGoals

    fun getPastGoals(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _pastGoals,
        coroutineErrorHandler
    ) {
        repository.getPastGoals()
    }

    val pastGoalsCnt = Transformations.map(_pastGoals) {
        when(it) {
            is ApiResponse.Loading -> {
                0
            }
            is ApiResponse.Failure -> {
                0
            }
            is ApiResponse.Success -> {
                it.data.data?.content?.size ?: 0
            }
        }
    }

    private val _getMarshmello = MutableLiveData<ApiResponse<BasePomeResponse<List<MyTabMarshmello>>>>()
    val getMarshmello: LiveData<ApiResponse<BasePomeResponse<List<MyTabMarshmello>>>> = _getMarshmello

    fun getMarshmello(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getMarshmello,
        coroutineErrorHandler
    ){
        repository.getMarshmello()
    }
}