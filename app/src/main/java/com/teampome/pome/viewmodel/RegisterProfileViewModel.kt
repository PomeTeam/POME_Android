package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.PresignedUrlImageData
import com.teampome.pome.repository.register.PresignedUrlRepository
import com.teampome.pome.repository.register.RegisterRepository
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterProfileViewModel @Inject constructor(
    private val repository : RegisterRepository,
    private val imageRepository : PresignedUrlRepository
) : BaseViewModel() {
    val userName = MutableLiveData<String>()

    private val _nicknameResponse = MutableLiveData<ApiResponse<BasePomeResponse<Boolean>>>()
    val nicknameResponse: LiveData<ApiResponse<BasePomeResponse<Boolean>>> = _nicknameResponse

    private val _presignedImageUrlResponse = MutableLiveData<ApiResponse<PresignedUrlImageData>>()
    val presignedImageUrlResponse: LiveData<ApiResponse<PresignedUrlImageData>> = _presignedImageUrlResponse

    fun checkNickname(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _nicknameResponse,
        coroutineErrorHandler
    ) {
        repository.checkNickname(userName.value ?: "")
    }

    fun getPresignedImageUrl(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _presignedImageUrlResponse,
        coroutineErrorHandler
    ) {
        imageRepository.getPresignedUrl(CommonUtil.getRandomString(4))
    }
}