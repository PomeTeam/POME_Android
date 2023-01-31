package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.UserInfoData
import com.teampome.pome.repository.login.LoginRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: LoginRepository
) : BaseViewModel() {

    private val _loginResponse = MutableLiveData<ApiResponse<BasePomeResponse<UserInfoData>>>()
    val loginResponse: LiveData<ApiResponse<BasePomeResponse<UserInfoData>>> = _loginResponse

    fun login(phoneNumber: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _loginResponse,
        coroutineErrorHandler
    ) {
        repository.login(phoneNumber)
    }
}