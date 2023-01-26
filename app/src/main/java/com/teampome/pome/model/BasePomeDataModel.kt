package com.teampome.pome.model

data class BasePomeDataModel<T>(
    val success: Boolean,
    val localDateTime: String,
    val httpStatus: String,
    val errorCode: String?,
    val message: String,
    val data: T?
)