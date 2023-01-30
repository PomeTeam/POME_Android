package com.teampome.pome.repository.register

import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface SendPreSignedImageDataSource {
    fun sendImage(body: MultipartBody.Part) : Flow<ApiResponse<Void>>
}