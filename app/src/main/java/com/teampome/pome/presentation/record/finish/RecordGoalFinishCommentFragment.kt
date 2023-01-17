package com.teampome.pome.presentation.record.finish

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordGoalFinishCommentBinding
import com.teampome.pome.util.base.BaseFragment

class RecordGoalFinishCommentFragment : BaseFragment<FragmentRecordGoalFinishCommentBinding>(R.layout.fragment_record_goal_finish_comment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finishCommentBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.finishCommentYesButtonAcb.setOnClickListener {
            moveToGoalFinishComplete()
        }

        binding.finishCommentNoButtonAcb.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initListener() {

    }

    private fun moveToGoalFinishComplete() {
        val action = RecordGoalFinishCommentFragmentDirections.actionRecordGoalFinishCommentFragmentToRecordGoalFinishCompleteFragment()

        findNavController().navigate(action)
    }
}