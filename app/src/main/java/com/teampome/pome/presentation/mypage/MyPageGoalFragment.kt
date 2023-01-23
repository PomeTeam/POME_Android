package com.teampome.pome.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.databinding.FragmentMypageGoalBinding

//마이페이지 완료된 목표 뷰
class MyPageGoalFragment : BaseFragment<FragmentMypageGoalBinding>(R.layout.fragment_mypage_goal) {
    override fun initListener() {
        //뒤로가기
        binding.mypageGoalArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}