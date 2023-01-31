package com.teampome.pome.repository.register

import com.teampome.pome.model.PresignedUrlImageData
import com.teampome.pome.network.ImageService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PresignedUrlRemoteDataSource @Inject constructor(
    private val service: ImageService
) : PresignedUrlDataSource {
    override fun getPresignedUrl(id: String): Flow<ApiResponse<PresignedUrlImageData>> = apiRequestFlow {
        service.getPresignedUrl(id)
    }
}