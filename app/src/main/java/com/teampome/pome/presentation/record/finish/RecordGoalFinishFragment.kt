package com.teampome.pome.presentation.record.finish

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordGoalFinishBinding
import com.teampome.pome.model.ContentCardItem
import com.teampome.pome.presentation.remind.RemindContentsCardAdapter
import com.teampome.pome.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordGoalFinishFragment : BaseFragment<FragmentRecordGoalFinishBinding>(R.layout.fragment_record_goal_finish) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        binding.recordGoalEmotionCardListRv.adapter
    }

    override fun initListener() {
        binding.recordGoalFinishCheckButtonAcb.setOnClickListener {
            moveToGoalFinishComment()
        }

        binding.recordGoalFinishBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun moveToGoalFinishComment() {
        val action = RecordGoalFinishFragmentDirections.actionRecordGoalFinishFragmentToRecordGoalFinishCommentFragment()

        findNavController().navigate(action)
    }
}