package com.teampome.pome.model.base

data class ErrorResponse(
    val code: Int,
    val message: List<String>
)