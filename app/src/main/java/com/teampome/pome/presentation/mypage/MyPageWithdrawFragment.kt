package com.teampome.pome.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageAlarmBinding
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.databinding.FragmentMypageGoalBinding
import com.teampome.pome.databinding.FragmentMypageSettingBinding
import com.teampome.pome.databinding.FragmentMypageWithdrawBinding

//마이페이지 탈퇴하기 뷰
class MyPageWithdrawFragment : BaseFragment<FragmentMypageWithdrawBinding>(R.layout.fragment_mypage_withdraw) {

    //탈퇴 이유
    private var withdrawReason1 = false
    private var withdrawReason2 = false
    private var withdrawReason3 = false
    private var withdrawReason4 = false

    override fun initView() {
        //register_profile_name_check_disable_btn_background 버튼 비활성화

    }

    override fun initListener() {
        //뒤로가기
        binding.mypageWithdrawArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.withdrawOkayBtn.setOnClickListener {
            val action = MyPageWithdrawFragmentDirections.actionMyPageWithdrawFragmentToMyPageWithdrawWarningFragment()
            findNavController().navigate(action)
        }
    }
}