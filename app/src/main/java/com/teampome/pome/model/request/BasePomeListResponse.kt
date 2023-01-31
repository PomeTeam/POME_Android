package com.teampome.pome.model.request

/**
 *  data가 array일 때, 사용
 */
data class BasePomeListResponse<T>(
    val success: Boolean,
    val localDateTime: String,
    val httpStatus: String,
    val errorCode: String?,
    val message: String,
    val data: List<T>?
)