package com.teampome.pome.presentation.mypage.setting

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.BuildConfig
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageSettingBinding
import com.teampome.pome.databinding.PomeTitleYesNoDialogBinding
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.common.Constants
import com.teampome.pome.viewmodel.mypage.MyPageSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

// 마이페이지 설정 뷰
@AndroidEntryPoint
class MyPageSettingFragment : BaseFragment<FragmentMypageSettingBinding>(R.layout.fragment_mypage_setting) {

    private val viewModel: MyPageSettingViewModel by viewModels()

    private lateinit var logoutDialogBinding: PomeTitleYesNoDialogBinding
    private lateinit var logoutDialog: Dialog

    override fun initView() {
        binding.viewModel = viewModel

        makeLogoutDialog()
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
            moveToQuestionGoogleForm()
        }

        // 알림 설정 현재는 주석
//        binding.mypageAlarmSettingCl.setOnClickListener {
//            moveToMyPageAlarmFragment()
//        }

        // 신고 하기
        binding.mypageReportSettingCl.setOnClickListener {
            moveToReportingGoogleForm()
        }

        // 약관 및 정책
        binding.mypagePolicySettingCl.setOnClickListener {
            moveToPolicyNotion()
        }

        // 오픈소스 라이센스
        binding.mypageOpenSourceSettingCl.setOnClickListener {
            moveToOpenSourceNotion()
        }

        // 버전 정보
        binding.mypageVersionSettingCl.setOnClickListener {
            // 토스트로 버전 정보 띄움
            Toast.makeText(requireContext(), BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show()
        }

        // 로그아웃
        binding.mypageLogoutSettingCl.setOnClickListener {
            logoutDialog.show()
        }

        // 탈퇴 하기
        binding.mypageWithdrawSettingCl.setOnClickListener {
            moveToMyPageWithdrawFragment()
        }
    }

    private fun moveToMyPageFriendFragment() {
        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageFriendFragment()
        findNavController().navigate(action)
    }

//    private fun moveToMyPageAlarmFragment() {
//        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageAlarmFragment()
//        findNavController().navigate(action)
//    }

    private fun moveToQuestionGoogleForm() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MY_PAGE_QUESTION_GOOGLE_FORM_URL))
        startActivity(intent)
    }

    private fun moveToReportingGoogleForm() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MY_PAGE_REPORTING_GOOGLE_FORM_URL))
        startActivity(intent)
    }

    private fun moveToPolicyNotion() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MY_PAGE_POLICY_NOTION_URL))
        startActivity(intent)
    }

    private fun moveToOpenSourceNotion() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MY_PAGE_OPEN_SOURCE_NOTION_URL))
        startActivity(intent)
    }

    private fun moveToSplashView() {
        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToSplashFramgnet()
        findNavController().navigate(action)
    }

    // 유저 정보를 전부 초기화한 후, 스플래시로 이동하는 로직
    private fun logout() {
        viewModel.removeAllUserData()
        moveToSplashView()
    }

    private fun makeLogoutDialog() {
        logoutDialog = Dialog(requireContext())
        logoutDialogBinding = PomeTitleYesNoDialogBinding.inflate(layoutInflater, null, false)

        logoutDialog.setContentView(logoutDialogBinding.root)

        logoutDialogBinding.apply {
            pomeTitleTv.text = "로그아웃 하시겠어요?"
            pomeYesTv.text = "네"
            pomeNoTv.text = "아니요"
        }

        CommonUtil.makePomeDialog(logoutDialog)

        // 네 클릭
        logoutDialogBinding.pomeYesTv.setOnClickListener {
            if(logoutDialog.isShowing) {
                logoutDialog.dismiss()
            }

            logout()
        }

        // 아니요 클릭
        logoutDialogBinding.pomeNoTv.setOnClickListener {
            if(logoutDialog.isShowing) {
                logoutDialog.dismiss()
            }
        }
    }

    private fun moveToMyPageWithdrawFragment() {
        val action = MyPageSettingFragmentDirections.actionMyPageSettingFragmentToMyPageWithdrawFragment()
        findNavController().navigate(action)
    }
}