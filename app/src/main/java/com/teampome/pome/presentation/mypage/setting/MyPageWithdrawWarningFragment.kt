package com.teampome.pome.presentation.mypage.setting

import android.app.Dialog
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageWithdrawWarningBinding
import com.teampome.pome.databinding.PomeTitleYesNoDialogBinding
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.viewmodel.mypage.MyPageWithdrawWarningViewModel
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 탈퇴하기 뷰
@AndroidEntryPoint
class MyPageWithdrawWarningFragment : BaseFragment<FragmentMypageWithdrawWarningBinding>(R.layout.fragment_mypage_withdraw_warning) {

    private val viewModel: MyPageWithdrawWarningViewModel by viewModels()

    private val args: MyPageWithdrawWarningFragmentArgs by navArgs()

    // 탈퇴 완료 Dialog
    private lateinit var withdrawDialog: Dialog
    private lateinit var withdrawDialogBinding: PomeTitleYesNoDialogBinding

    override fun initView() {
        makeWithdrawDialog()

        binding.viewModel = viewModel
    }

    override fun initListener() {

        viewModel.deleteUserResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Loading -> { }
                is ApiResponse.Failure -> { hideLoading() }
                is ApiResponse.Success -> {
                    hideLoading()

                    if(it.data.data == true) {
                        viewModel.removeAllUserData()
                        withdrawDialog.show()
                    } else {
                        Toast.makeText(requireContext(), "회원 탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 뒤로가기
        binding.mypageWithdrawWarningArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // 탈퇴하기
        binding.withdrawOkayBtn.setOnClickListener {
            showLoading()

            viewModel.deleteUser(args.withdrawReason, object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            })
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

                moveToSplashView()
            }
        }
    }

    private fun moveToSplashView() {
        val action = MyPageWithdrawWarningFragmentDirections.actionMyPageWithdrawWarningFragmentToSplashFragment()

        findNavController().navigate(action)
    }
}