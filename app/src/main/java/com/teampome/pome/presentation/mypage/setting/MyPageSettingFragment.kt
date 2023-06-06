package com.teampome.pome.presentation.mypage.setting

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageSettingBinding
import com.teampome.pome.viewmodel.mypage.MyPageSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

// 마이페이지 설정 뷰
@AndroidEntryPoint
class MyPageSettingFragment : BaseFragment<FragmentMypageSettingBinding>(R.layout.fragment_mypage_setting) {

    private val viewModel: MyPageSettingViewModel by viewModels()

    override fun initView() {
        binding.viewModel = viewModel
    }

    override fun initListener() {
        // 뒤로가기
        binding.mypageSettingArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // 친구 관리
        binding.mypageFriendSettingCl.setOnClickListener {
            moveToMyPageFriendFragment()
        }

        // 문의 하기
        binding.mypageQuestionSettingCl.setOnClickListener {

        }

        // 알림 설정
        binding.mypageAlarmSettingCl.setOnClickListener {
            moveToMyPageAlarmFragment()
        }

        // 신고 하기
        binding.mypageReportSettingCl.setOnClickListener {

        }

        // 약관 및 정책
        binding.mypagePolicySettingCl.setOnClickListener {

        }

        // 오픈소스 라이센스
        binding.mypageOpenSourceSettingCl.setOnClickListener {

        }

        // 버전 정보
        binding.mypageVersionSettingCl.setOnClickListener {

        }

        // 로그아웃
        binding.mypageLogoutSettingCl.setOnClickListener {

        }

        // 탈퇴 하기
        binding.mypageWithdrawSettingCl.setOnClickListener {
            moveToMyPageWithdrawFragment()
        }
    }

    private fun moveToMyPageFriendFragment() {
        val action =
            MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageFriendFragment()
        findNavController().navigate(action)
    }

    private fun moveToMyPageAlarmFragment() {
        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageAlarmFragment()
        findNavController().navigate(action)
    }

    private fun moveToMyPageWithdrawFragment() {
        val action =
            MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageWithdrawFragment()
        findNavController().navigate(action)
    }
}