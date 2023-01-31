package com.teampome.pome.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageAlarmBinding
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.databinding.FragmentMypageFriendBinding
import com.teampome.pome.databinding.FragmentMypageGoalBinding

//마이페이지 친구관리 뷰
class MyPageFriendFragment : BaseFragment<FragmentMypageFriendBinding>(R.layout.fragment_mypage_friend) {
    override fun initView() {

    }

    override fun initListener() {
        //뒤로가기
        binding.mypageFriendArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}