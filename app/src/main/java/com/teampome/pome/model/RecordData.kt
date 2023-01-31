package com.teampome.pome.model

data class RecordData(
    val createdAt: String,
    val emotionResponse: EmotionResponse,
    val id: Int,
    val nickname: String,
    val oneLineMind: String,
    val userComment: String,
    val useDate: String,
    val usePrice: Int
)

data class EmotionResponse(
    val firstEmotion: Int,
    val friendEmotions: List<FriendEmotion>,
    val myEmotion: Int,
    val secondEmotion: Int
)

data class FriendEmotion(
    val emotionId: Int,
    val nickname: String
)