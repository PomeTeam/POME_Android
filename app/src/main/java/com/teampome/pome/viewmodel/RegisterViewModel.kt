package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.SmsData
import com.teampome.pome.repository.register.RegisterRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : BaseViewModel() {
    val registerPhone = MutableLiveData<String>()
    val registerCertNum = MutableLiveData<String>()

    private val _smsResponse = MutableLiveData<ApiResponse<BasePomeResponse<SmsData>>>()
    val smsResponse: LiveData<ApiResponse<BasePomeResponse<SmsData>>> = _smsResponse

    var smsValidate: String = ""

    fun sendSms(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _smsResponse,
        coroutineErrorHandler
    ) {
        repository.sendSms(registerPhone.value ?: "")
    }
}