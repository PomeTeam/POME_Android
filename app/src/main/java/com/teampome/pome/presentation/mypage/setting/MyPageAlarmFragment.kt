package com.teampome.pome.presentation.mypage.setting

import androidx.core.content.res.ResourcesCompat
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

        //돌아보기 스위치
        binding.mypageAlarmSeeSwitchSc.setOnCheckedChangeListener { button, layout ->

            binding.mypageAlarmSeeSwitchSc.jumpDrawablesToCurrentState()

            if(layout) {
                binding.mypageAlarmSeeCl.background = ResourcesCompat.getDrawable(resources, R.drawable.item_pink10_r8_background, null)
            } else {
                binding.mypageAlarmSeeCl.background = ResourcesCompat.getDrawable(resources, R.drawable.item_grey1_r8_background, null)
            }
        }

        //목표종료 스위치
        binding.mypageAlarmRecordSc.setOnCheckedChangeListener { button, layout ->

            binding.mypageAlarmRecordSc.jumpDrawablesToCurrentState()

            if(layout) {
                binding.mypageAlarmRecordCl.background = ResourcesCompat.getDrawable(resources, R.drawable.item_pink10_r8_background, null)
            } else {
                binding.mypageAlarmRecordCl.background = ResourcesCompat.getDrawable(resources, R.drawable.item_grey1_r8_background, null)
            }
        }
    }
}