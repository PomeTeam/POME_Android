package com.teampome.pome.repository.record

import com.teampome.pome.model.*
import javax.inject.Inject

class RecordTestDataSource @Inject constructor(

): RecordDataSource {
    override suspend fun getRecordTestData(): RecordTestData? {
//        return null
        return RecordTestData(
            listOf(
                RecordGoalItem(
                    category = "커피",
                    recordGoalCard = RecordGoalCard(
                        isPrivate = true,
                        timeLimit = 0,
                        title = "커피 대신 물을 마시자",
                        useAmount = "30,000원",
                        goalAmount = "100,000원"
                    )
                ),
                RecordGoalItem(
                    category = "아이스크림",
                    recordGoalCard = RecordGoalCard(
                        isPrivate = false,
                        timeLimit = 10,
                        title = "아이스크림 대신 물을 마시자",
                        useAmount = "30,000원",
                        goalAmount = "100,000원"
                    )
                ),
                RecordGoalItem(
                    category = "택시",
                    recordGoalCard = RecordGoalCard(
                        isPrivate = true,
                        timeLimit = 22,
                        title = "택시 대신 물을 마시자",
                        useAmount = "30,000원",
                        goalAmount = "100,000원"
                    )
                )
            ),
            recordWeekData = RecordWeekData(
                remindCount = 3,
                recordWeekItem = listOf(
                    RecordWeekItem(
                        firstThink = "후회해요",
                        lastThink = null,
                        title = "16000원",
                        subTitle = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                        date = "6월 24일"
                    ),
                    RecordWeekItem(
                        firstThink = "후회해요",
                        lastThink = null,
                        title = "16000원",
                        subTitle = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                        date = "6월 24일"
                    ),
                    RecordWeekItem(
                        firstThink = "후회해요",
                        lastThink = null,
                        title = "16000원",
                        subTitle = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                        date = "6월 24일"
                    ),
                    RecordWeekItem(
                        firstThink = "후회해요",
                        lastThink = null,
                        title = "16000원",
                        subTitle = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                        date = "6월 24일"
                    ),
                    RecordWeekItem(
                        firstThink = "후회해요",
                        lastThink = null,
                        title = "16000원",
                        subTitle = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                        date = "6월 24일"
                    ),
                    RecordWeekItem(
                        firstThink = "후회해요",
                        lastThink = null,
                        title = "16000원",
                        subTitle = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                        date = "6월 24일"
                    ),
                    RecordWeekItem(
                        firstThink = "후회해요",
                        lastThink = null,
                        title = "16000원",
                        subTitle = "아휴 힘빠져 이젠 진짜 포기다 포기 도대체 뭐가 문제일까 현실을 되돌아볼 필요를 느낀다ㅠ 이정도 노력했으면 된거 아닌가 진짜 개 힘빠지네 그래서 오늘은 물 대신 라떼 한잔을 마셨습니다 ㅋ 라뗴 존맛탱~~ 다들 나흐바 시그니쳐 커피를 마셔주세요 설탕 솔솔 뿌려서 개맛있음",
                        date = "6월 24일"
                    )
                )
            )
        )
    }
}