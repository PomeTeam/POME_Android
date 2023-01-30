package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.PresignedUrlImageData
import com.teampome.pome.repository.register.PresignedUrlRepository
import com.teampome.pome.repository.register.RegisterRepository
import com.teampome.pome.repository.register.SendPreSignedImageRepository
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterProfileViewModel @Inject constructor(
    private val repository : RegisterRepository,
    private val imageRepository : PresignedUrlRepository,
    private val sendImageRepository : SendPreSignedImageRepository
) : BaseViewModel() {
    val userName = MutableLiveData<String>()

    private val _nicknameResponse = MutableLiveData<ApiResponse<BasePomeResponse<Boolean>>>()
    val nicknameResponse: LiveData<ApiResponse<BasePomeResponse<Boolean>>> = _nicknameResponse

    private val _presignedImageUrlResponse = MutableLiveData<ApiResponse<PresignedUrlImageData>>()
    val presignedImageUrlResponse: LiveData<ApiResponse<PresignedUrlImageData>> = _presignedImageUrlResponse

    private val _presignedImageResponse = MutableLiveData<ApiResponse<Void>>()
    val presignedImageResponse: LiveData<ApiResponse<Void>> = _presignedImageResponse

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

    fun sendPreSignedImage(file: ByteArray, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _presignedImageResponse,
        coroutineErrorHandler
    ) {
        sendImageRepository.sendImage(file)
    }
}