package com.teampome.pome.presentation.record.leave

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordLeaveEmotionBinding
import com.teampome.pome.model.RecordWeekItem
import com.teampome.pome.presentation.record.RecordContentsCardAdapter
import com.teampome.pome.util.base.BaseFragment

class RecordLeaveEmotionFragment : BaseFragment<FragmentRecordLeaveEmotionBinding>(R.layout.fragment_record_leave_emotion) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recordLeaveEmotionRv.adapter = RecordContentsCardAdapter().apply {
            submitList(listOf(
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
            ))
        }
    }

    override fun initListener() {
        // 뒤로가기 클릭
        binding.recordLeaveEmotionBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}