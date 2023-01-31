package com.teampome.pome.model

data class ErrorResponse(
    val code: Int,
    val message: List<String>
)