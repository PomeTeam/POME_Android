package com.teampome.pome.presentation.mypage.setting

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageWithdrawWarningBinding
import com.teampome.pome.util.base.BaseFragment

//마이페이지 탈퇴하기 뷰
class MyPageWithdrawWarningFragment : BaseFragment<FragmentMypageWithdrawWarningBinding>(R.layout.fragment_mypage_withdraw_warning) {

    override fun initView() {

    }

    override fun initListener() {
        //뒤로가기
        binding.mypageWithdrawWarningArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        //탈퇴하기
        binding.withdrawOkayBtn.setOnClickListener {

        }
    }
}