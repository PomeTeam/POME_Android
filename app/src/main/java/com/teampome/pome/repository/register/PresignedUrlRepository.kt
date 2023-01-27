package com.teampome.pome.repository.register

import javax.inject.Inject

class PresignedUrlRepository @Inject constructor(
    private val presignedUrlDataSource: PresignedUrlDataSource
) {
    fun getPresignedUrl(id: String) = presignedUrlDataSource.getPresignedUrl(id)
}