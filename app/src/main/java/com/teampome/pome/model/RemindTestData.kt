package com.teampome.pome.model

data class RemindTestData(
    val goal: String?,
    val contentItems: List<RemindTestItem>?
)

data class RemindTestItem(
    val tag: List<String>?,
    val time: String,
    val total: String,
    val contentText: String,
    val firstEmotion: String,
    val lastEmotion: String,
    val friendsEmotion: List<String>?
)