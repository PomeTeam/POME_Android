package com.teampome.pome.repository.register

import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SendPreSignedImageDataSource {
    fun sendImage(body: RequestBody) : Flow<ApiResponse<Void>>
}