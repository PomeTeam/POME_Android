package com.teampome.pome.model.request

data class ConsumeRecordDataBody(
    val emotionId: Int,
    val goalId: Int,
    val useComment: String,
    val useDate: String,
    val usePrice: Long
)