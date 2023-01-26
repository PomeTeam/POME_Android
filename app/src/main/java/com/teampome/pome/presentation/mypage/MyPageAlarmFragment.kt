package com.teampome.pome.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageAlarmBinding
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.databinding.FragmentMypageGoalBinding

//마이페이지 알림설정 뷰
class MyPageAlarmFragment : BaseFragment<FragmentMypageAlarmBinding>(R.layout.fragment_mypage_alarm) {
    override fun initView() {

    }

    override fun initListener() {
        //뒤로가기
        binding.mypageAlarmArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}