package com.teampome.pome.repository.remind

import com.teampome.pome.model.RemindTestData
import com.teampome.pome.model.RemindTestItem
import com.teampome.pome.util.Constants.HAPPY_EMOTION
import com.teampome.pome.util.Constants.SAD_EMOTION
import com.teampome.pome.util.Constants.WHAT_EMOTION
import javax.inject.Inject

class RemindTestDataSource @Inject constructor(

) : RemindDataSource {
    override suspend fun getTestRemindData(): RemindTestData? {
        // test data 주입
        return RemindTestData(
            goal = "술은 한달에 적당히 먹자",
            contentItems = listOf(
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                ),
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                ),
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                ),
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                ),
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                ),
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                ),
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                ),
                RemindTestItem(
                    tag = listOf("커피 대신 물을 마시자"),
                    time = "1시간 전",
                    total = "320,000원",
                    contentText = "아아아아아아아아아아아아아아아아아아아아",
                    firstEmotion = HAPPY_EMOTION,
                    lastEmotion = SAD_EMOTION,
                    friendsEmotion = listOf(
                        HAPPY_EMOTION,
                        WHAT_EMOTION,
                        SAD_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION,
                        HAPPY_EMOTION
                    )
                )
            )
        )
    }
}