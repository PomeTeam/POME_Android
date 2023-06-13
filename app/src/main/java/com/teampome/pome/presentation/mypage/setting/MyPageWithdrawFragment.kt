package com.teampome.pome.presentation.mypage.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageWithdrawBinding
import com.teampome.pome.viewmodel.mypage.MyPageWithdrawViewModel
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 탈퇴하기 뷰
@AndroidEntryPoint
class MyPageWithdrawFragment : BaseFragment<FragmentMypageWithdrawBinding>(R.layout.fragment_mypage_withdraw) {

    private val viewModel: MyPageWithdrawViewModel by viewModels()

    override fun initView() {
        binding.viewModel = viewModel
    }

    override fun initListener() {
        //뒤로가기
        binding.mypageWithdrawArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.withdrawReason1Cl.setOnClickListener {
            viewModel.reason1Click()
        }

        binding.withdrawReason2Cl.setOnClickListener {
            viewModel.reason2Click()
        }

        binding.withdrawReason3Cl.setOnClickListener {
            viewModel.reason3Click()
        }

        binding.withdrawReason4Cl.setOnClickListener {
            viewModel.reason4Click()
        }

        binding.withdrawOkayBtn.setOnClickListener {
            val action = MyPageWithdrawFragmentDirections.actionMyPageWithdrawFragmentToMyPageWithdrawWarningFragment()
            findNavController().navigate(action)
        }
    }
}