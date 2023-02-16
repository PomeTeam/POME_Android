package com.teampome.pome.presentation.record.finish

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordGoalFinishBinding
import com.teampome.pome.model.ContentCardItem
import com.teampome.pome.presentation.remind.RemindContentsCardAdapter
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.record.RecordGoalFinishViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordGoalFinishFragment : BaseFragment<FragmentRecordGoalFinishBinding>(R.layout.fragment_record_goal_finish) {
    private val navArgs: RecordGoalFinishFragmentArgs by navArgs()
    private val viewModel: RecordGoalFinishViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setCurGoalData(navArgs.goalData)
        binding.goalData = viewModel.curGoalData.value
        binding.executePendingBindings()
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