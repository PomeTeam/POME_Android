package com.teampome.pome.repository.register

import com.teampome.pome.network.PreSignedImageService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject

class SendPreSignedImageRemoteDataSource @Inject constructor(
    private val service: PreSignedImageService
) : SendPreSignedImageDataSource {
    override fun sendImage(body: RequestBody): Flow<ApiResponse<Void>> = apiRequestFlow {
        service.sendImage(body)
    }
}