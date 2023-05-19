package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.base.BasePomeResponse
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
class MyTabViewModel @Inject constructor(
    private val repository: MyPageRepository,
    private val userManager: UserManager
): BaseViewModel() {

    private val userName = runBlocking {
        userManager.getUserNickName().first()
    }

    init {

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