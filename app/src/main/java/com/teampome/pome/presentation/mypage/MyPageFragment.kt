package com.teampome.pome.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 탭
@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    override fun initView() {

    }

    override fun initListener() {
        //설정화면으로
        binding.mypageSettingIv.setOnClickListener {
            val action = MyPageFragmentDirections.actionMypageFragmentToMyPageSettingFragment()
            findNavController().navigate(action)
        }

        //목표설정화면으로
        binding.mypageMainCl.setOnClickListener {
            val action = MyPageFragmentDirections.actionMypageFragmentToMyPageGoalFragment()
            findNavController().navigate(action)
        }
    }
}