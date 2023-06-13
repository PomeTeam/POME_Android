package com.teampome.pome.presentation.mypage.setting

import android.app.Dialog
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageWithdrawWarningBinding
import com.teampome.pome.databinding.PomeTitleYesNoDialogBinding
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.viewmodel.mypage.MyPageWithdrawWarningViewModel
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 탈퇴하기 뷰
@AndroidEntryPoint
class MyPageWithdrawWarningFragment : BaseFragment<FragmentMypageWithdrawWarningBinding>(R.layout.fragment_mypage_withdraw_warning) {

    private val viewModel: MyPageWithdrawWarningViewModel by viewModels()

    // 탈퇴 완료 Dialog
    private lateinit var withdrawDialog: Dialog
    private lateinit var withdrawDialogBinding: PomeTitleYesNoDialogBinding

    override fun initView() {
        makeWithdrawDialog()

        binding.viewModel = viewModel
    }

    override fun initListener() {
        // 뒤로가기
        binding.mypageWithdrawWarningArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // 탈퇴하기
        binding.withdrawOkayBtn.setOnClickListener {
            withdrawDialog.show()
        }
    }

    private fun makeWithdrawDialog() {
        withdrawDialog = Dialog(requireContext())
        withdrawDialogBinding = PomeTitleYesNoDialogBinding.inflate(layoutInflater, null, false)

        withdrawDialog.setContentView(withdrawDialogBinding.root)
        withdrawDialog.setCancelable(false)

        CommonUtil.makePomeDialog(withdrawDialog)

        withdrawDialogBinding.pomeTitleTv.text = "탈퇴가 완료되었습니다."

        withdrawDialogBinding.pomeNoTv.visibility = View.GONE

        withdrawDialogBinding.pomeYesTv.apply {
            text = "확인"

            setOnClickListener {
                withdrawDialog.dismiss()

                withdrawProcess()
            }
        }
    }

    private fun withdrawProcess() {
//        viewModel.removeAllUserData()
        // splashview 이동
    }
}