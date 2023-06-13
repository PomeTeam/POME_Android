package com.teampome.pome.presentation.mypage.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageWithdrawWarningBinding
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.mypage.MyPageWithdrawWarningViewModel
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 탈퇴하기 뷰
@AndroidEntryPoint
class MyPageWithdrawWarningFragment : BaseFragment<FragmentMypageWithdrawWarningBinding>(R.layout.fragment_mypage_withdraw_warning) {

    private val viewModel: MyPageWithdrawWarningViewModel by viewModels()

    override fun initView() {
        binding.viewModel = viewModel
    }

    override fun initListener() {
        // 뒤로가기
        binding.mypageWithdrawWarningArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // 탈퇴하기
        binding.withdrawOkayBtn.setOnClickListener {

        }
    }
}