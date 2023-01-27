package com.teampome.pome.repository.register

import com.teampome.pome.model.PresignedUrlImageData
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface PresignedUrlDataSource {
    fun getPresignedUrl(id: String) : Flow<ApiResponse<PresignedUrlImageData>>
}