package com.teampome.pome.presentation.mypage.goal

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.databinding.FragmentMypageGoalBinding
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.presentation.mypage.recyclerview.goal.MyPageGoalAdapter
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.viewmodel.mypage.MyPageGoalViewModel
import dagger.hilt.android.AndroidEntryPoint

// 마이페이지 완료된 목표 뷰

@AndroidEntryPoint
class MyPageGoalFragment : BaseFragment<FragmentMypageGoalBinding>(R.layout.fragment_mypage_goal) {

    private val viewModel: MyPageGoalViewModel by viewModels()

    // 삭제 다이얼로그
    private lateinit var goalRemoveDialog: Dialog
    private lateinit var goalRemoveDialogBinding: PomeRemoveDialogBinding

    override fun initView() {
        binding.viewModel = viewModel

        callGetEndGoals()

        binding.mypageGoalRv.adapter = MyPageGoalAdapter(::showGoalRemoveDialog)
    }

    override fun initListener() {
        viewModel.getEndGoalResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Loading -> {}
                is ApiResponse.Failure -> { hideLoading() }
                is ApiResponse.Success -> {
                    (binding.mypageGoalRv.adapter as MyPageGoalAdapter).submitList(it.data.data?.content)

                    hideLoading()
                }
            }
        }

        viewModel.deleteEndGoalResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Loading -> {  }
                is ApiResponse.Failure -> { hideLoading() }
                is ApiResponse.Success -> {
                    callGetEndGoals()
                    hideLoading()
                }
            }
        }

        // 뒤로가기
        binding.mypageGoalArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun makeGoalRemoveDialog(
        onYesClick: () -> Unit
    ) {
        goalRemoveDialog = Dialog(requireContext())
        goalRemoveDialogBinding = PomeRemoveDialogBinding.inflate(layoutInflater, null, false)

        goalRemoveDialog.setContentView(goalRemoveDialogBinding.root)
        goalRemoveDialog.setCancelable(false)

        CommonUtil.makePomeDialog(goalRemoveDialog)

        goalRemoveDialogBinding.removeDialogTitleAtv.text = "종료된 목표를 삭제하시겠어요?"
        goalRemoveDialogBinding.removeDialogSubtitleAtv.text = "지금까지 작성한 기록들은 모두 사라져요"

        goalRemoveDialogBinding.removeYesTextAtv.apply {
            text = "삭제할래요"

            setOnClickListener {
                onYesClick.invoke()
            }
        }

        goalRemoveDialogBinding.removeNoTextAtv.apply {
            text = "아니요"

            setOnClickListener {
                goalRemoveDialog.dismiss()
            }
        }
    }

    private fun showGoalRemoveDialog(goalData: GoalData) {
        makeGoalRemoveDialog {
            showLoading()

            viewModel.deleteEndGoal(goalData.id, object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    hideLoading()
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            })

            goalRemoveDialog.dismiss()
        }

        goalRemoveDialog.show()
    }

    private fun callGetEndGoals() {
        showLoading()

        viewModel.findEndGoals(object: CoroutineErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }
}