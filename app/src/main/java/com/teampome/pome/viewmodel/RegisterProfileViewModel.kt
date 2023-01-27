package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.repository.register.RegisterRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterProfileViewModel @Inject constructor(
    private val repository : RegisterRepository
) : BaseViewModel() {
    val userName = MutableLiveData<String>()

    private val _nicknameResponse = MutableLiveData<ApiResponse<BasePomeResponse<Boolean>>>()
    val nicknameResponse: LiveData<ApiResponse<BasePomeResponse<Boolean>>> = _nicknameResponse

    fun checkNickname(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _nicknameResponse,
        coroutineErrorHandler
    ) {
        repository.checkNickname(userName.value ?: "")
    }
}