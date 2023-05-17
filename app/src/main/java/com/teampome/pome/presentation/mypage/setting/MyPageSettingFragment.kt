package com.teampome.pome.presentation.mypage.setting

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageSettingBinding

//마이페이지 설정 뷰
class MyPageSettingFragment : BaseFragment<FragmentMypageSettingBinding>(R.layout.fragment_mypage_setting) {
    override fun initView() {

    }

    override fun initListener() {
        //뒤로가기
        binding.mypageSettingArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        //알림설정
        binding.mypageAlarmSettingCl.setOnClickListener {
            val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageAlarmFragment()
            findNavController().navigate(action)
        }

        //친구관리
        binding.mypageFriendSettingCl.setOnClickListener {
            val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageFriendFragment()
            findNavController().navigate(action)
        }

        //탈퇴하기
        binding.mypageWithdrawSettingCl.setOnClickListener {
            val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageWithdrawFragment()
            findNavController().navigate(action)
        }
    }
}