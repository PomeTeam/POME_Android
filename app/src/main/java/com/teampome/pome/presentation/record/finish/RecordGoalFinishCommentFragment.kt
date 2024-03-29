package com.teampome.pome.presentation.record.finish

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordGoalFinishCommentBinding
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.record.RecordGoalFinishCommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordGoalFinishCommentFragment : BaseFragment<FragmentRecordGoalFinishCommentBinding>(R.layout.fragment_record_goal_finish_comment) {
    private lateinit var removeDialogBinding: PomeRemoveDialogBinding
    private lateinit var removeDialog: Dialog

    private val args: RecordGoalFinishFragmentArgs by navArgs()
    private val viewModel : RecordGoalFinishCommentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        CommonUtil.disabledPomeBtn(binding.finishCommentYesButtonAcb)

        makeRemoveDialog()

        binding.finishCommentBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.finishCommentNoButtonAcb.setOnClickListener {
            removeDialog.show()
        }
    }

    override fun initListener() {
        binding.finishCommentContentAet.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    viewModel.setOneLineComment(it.toString())
                }

                if(s.isNullOrEmpty()) {
                    CommonUtil.disabledPomeBtn(binding.finishCommentYesButtonAcb)
                } else {
                    CommonUtil.enabledPomeBtn(binding.finishCommentYesButtonAcb)
                }
            }
        })

        binding.finishCommentYesButtonAcb.setOnClickListener {
            viewModel.oneLineComment.value?.let { comment ->
                viewModel.finishGoal(
                    args.goalData.id,
                    comment,
                    object : CoroutineErrorHandler {
                        override fun onError(message: String) {
                            Log.e("error", "finishGoal error $message")
                        }
                    }
                )
            }
        }

        viewModel.deleteGoalResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    moveToRecord()

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }

        viewModel.oneLineComment.observe(viewLifecycleOwner) {}

        viewModel.finishGoalResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    moveToGoalFinishComplete()

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }
    }

    private fun makeRemoveDialog() {
        removeDialogBinding = PomeRemoveDialogBinding.inflate(layoutInflater, null, false)

        removeDialog = Dialog(requireContext())
        removeDialog.setContentView(removeDialogBinding.root)

        removeDialogBinding.removeDialogTitleAtv.text = "종료된 목표를 삭제하시겠어요?"
        removeDialogBinding.removeDialogSubtitleAtv.text = "지금까지 작성한 기록들은 모두 사라져요"

        removeDialogBinding.removeYesTextAtv.text = "삭제할래요"
        removeDialogBinding.removeNoTextAtv.text = "아니요"

        CommonUtil.makePomeDialog(removeDialog)

        // 그만둘래요 클릭
        removeDialogBinding.removeYesTextAtv.setOnClickListener {
            viewModel.deleteGoal(args.goalData.id, object: CoroutineErrorHandler {
                override fun onError(message: String) {
                    Log.e("error", "deleteGoal error $message")
                }
            })

            removeDialog.dismiss()
        }

        // 이어서 쓸래요 클릭
        removeDialogBinding.removeNoTextAtv.setOnClickListener {
            removeDialog.dismiss()
        }
    }

    private fun moveToGoalFinishComplete() {
        val action = RecordGoalFinishCommentFragmentDirections.actionRecordGoalFinishCommentFragmentToRecordGoalFinishCompleteFragment()

        findNavController().navigate(action)
    }

    private fun moveToRecord() {
        val action = RecordGoalFinishCommentFragmentDirections.actionRecordGoalFinishCommentFragmentToRecordFragment()

        findNavController().navigate(action)
    }
}