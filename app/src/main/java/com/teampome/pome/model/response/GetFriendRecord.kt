package com.teampome.pome.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//내 친구 기록 조회
@Parcelize
data class GetFriendRecord(
    val id : Int,
    val nickname: String,
    val usePrice: Int,
    val useDate: String,
    val useComment: String,
    val oneLineMind: String,
    val createdAt: String,
    val emotionResponse: FriendEmotion
) : Parcelable

@Parcelize
data class FriendEmotion(
    val firstEmotion : Int,
    val secondEmotion : Int,
    val myEmotion : Int,
    val friendEmotion : List<Int>
) : Parcelable

/*
"id": 3,
        "nickname": "찬영짱121",
        "usePrice": 2000,
        "useDate": "2023.01.26",
        "useComment": "앞으로 이렇게만!",
        "oneLineMind": "커피는 백다방만",
        "createdAt": "2023-01-26T20:11:16",
        "emotionResponse": {
          "firstEmotion": 1,
          "secondEmotion": null,
          "myEmotion": null,
          "friendEmotions": []
        }
 */

/*
"firstEmotion": 1,
          "secondEmotion": null,
          "myEmotion": null,
          "friendEmotions": []
 */