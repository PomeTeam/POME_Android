package com.teampome.pome.repository.register

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SendPreSignedImageRepository @Inject constructor(
    private val sendPreSignedImageDataSource: SendPreSignedImageDataSource
) {
    fun sendImage(file: ByteArray) =
        sendPreSignedImageDataSource.sendImage(
            MultipartBody.Part.create(file.toRequestBody("image/jpeg".toMediaTypeOrNull()))
        )
}