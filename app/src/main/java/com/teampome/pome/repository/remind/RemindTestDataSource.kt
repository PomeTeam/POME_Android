package com.teampome.pome.repository.remind

import com.teampome.pome.model.ContentCardItem
import com.teampome.pome.model.GoalCardItem
import com.teampome.pome.model.RemindTestData
import com.teampome.pome.model.RemindTestItem
import com.teampome.pome.util.Constants.HAPPY_EMOTION
import com.teampome.pome.util.Constants.SAD_EMOTION
import com.teampome.pome.util.Constants.WHAT_EMOTION
import javax.inject.Inject

class RemindTestDataSource @Inject constructor(

) : RemindDataSource {
    override suspend fun getTestRemindData(): RemindTestData? {

//        return null
        // test data 주입
        return RemindTestData(
            contentItems = listOf(
                RemindTestItem(
                    mainCategory = "커피",
                    goalCardItem = GoalCardItem(
                        categories = listOf(
                            "공개",
                            "D-12"
                        ),
                        goal = "커피 대신 물을 마시자"
                    ),
                    contentCardItem = listOf(
                        ContentCardItem(
                            firstThink = "후회해요",
                            lastThink = "행복해요",
                            money = "16,000원",
                            contentText = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                            name = "커피 대신 물을 마시자",
                            time = "44분 전",
                            friendEmotions = listOf(
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                            )
                        ),
                        ContentCardItem(
                            firstThink = "후회해요",
                            lastThink = "행복해요",
                            money = "16,000원",
                            contentText = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                            name = "커피 대신 물을 마시자",
                            time = "44분 전",
                            friendEmotions = listOf(
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                            )
                        ),
                    ),
                ),
                RemindTestItem(
                    mainCategory = "아이스크림",
                    goalCardItem = GoalCardItem(
                        categories = listOf(
                            "공개",
                            "D-12"
                        ),
                        goal = "커피 대신 물을 마시자"
                    ),
                    contentCardItem = listOf(
                        ContentCardItem(
                            firstThink = "후회해요",
                            lastThink = "행복해요",
                            money = "16,000원",
                            contentText = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                            name = "커피 대신 물을 마시자",
                            time = "44분 전",
                            friendEmotions = listOf(
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                            )
                        ),
                        ContentCardItem(
                            firstThink = "후회해요",
                            lastThink = "행복해요",
                            money = "16,000원",
                            contentText = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                            name = "커피 대신 물을 마시자",
                            time = "44분 전",
                            friendEmotions = listOf(
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                            )
                        ),
                    )
                ),
                RemindTestItem(
                    mainCategory = "택시",
                    goalCardItem = GoalCardItem(
                        categories = listOf(
                            "공개",
                            "D-12"
                        ),
                        goal = "커피 대신 물을 마시자"
                    ),
                    contentCardItem = listOf(
                        ContentCardItem(
                            firstThink = "후회해요",
                            lastThink = "행복해요",
                            money = "16,000원",
                            contentText = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                            name = "커피 대신 물을 마시자",
                            time = "44분 전",
                            friendEmotions = listOf(
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                            )
                        ),
                        ContentCardItem(
                            firstThink = "후회해요",
                            lastThink = "행복해요",
                            money = "16,000원",
                            contentText = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                            name = "커피 대신 물을 마시자",
                            time = "44분 전",
                            friendEmotions = listOf(
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                                "모르겠어요",
                            )
                        ),
                    )
                )
            )
        )

    }
}