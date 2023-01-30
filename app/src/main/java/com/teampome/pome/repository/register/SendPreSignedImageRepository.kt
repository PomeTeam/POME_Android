package com.teampome.pome.repository.register

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SendPreSignedImageRepository @Inject constructor(
    private val sendPreSignedImageDataSource: SendPreSignedImageDataSource
) {
    fun sendImage(file: ByteArray) =
        sendPreSignedImageDataSource.sendImage(
            file.toRequestBody("application/octet".toMediaTypeOrNull())
        )
}