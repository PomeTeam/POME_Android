package com.teampome.pome.presentation.record.finish

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordGoalFinishCommentBinding
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment

class RecordGoalFinishCommentFragment : BaseFragment<FragmentRecordGoalFinishCommentBinding>(R.layout.fragment_record_goal_finish_comment) {
    private lateinit var removeDialogBinding: PomeRemoveDialogBinding
    private lateinit var removeDialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        makeRemoveDialog()

        binding.finishCommentBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.finishCommentYesButtonAcb.setOnClickListener {
            moveToGoalFinishComplete()
        }

        binding.finishCommentNoButtonAcb.setOnClickListener {
            removeDialog.show()
        }
    }

    override fun initListener() {

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
            Toast.makeText(requireContext(), "삭제할래요", Toast.LENGTH_SHORT).show()

            removeDialog.dismiss()

            findNavController().popBackStack()
        }

        // 이어서 쓸래요 클릭
        removeDialogBinding.removeNoTextAtv.setOnClickListener {
            Toast.makeText(requireContext(), "아니요", Toast.LENGTH_SHORT).show()

            removeDialog.dismiss()
        }
    }

    private fun moveToGoalFinishComplete() {
        val action = RecordGoalFinishCommentFragmentDirections.actionRecordGoalFinishCommentFragmentToRecordGoalFinishCompleteFragment()

        findNavController().navigate(action)
    }
}