package com.teampome.pome.presentation.record.finish

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordGoalFinishBinding
import com.teampome.pome.presentation.remind.RemindContentsCardAdapter
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
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
        viewModel.curGoalData.observe(viewLifecycleOwner) {
            viewModel.getRemindRecords(it.id, firstEmotion = null, secondEmotion = null, object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    Log.e("error", "getRemindRecords error $message")
                }
            })
        }

        binding.recordGoalEmotionCardListRv.adapter = RemindContentsCardAdapter()
    }

    override fun initListener() {
        binding.recordGoalFinishCheckButtonAcb.setOnClickListener {
            moveToGoalFinishComment()
        }

        binding.recordGoalFinishBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getRemindRecordsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("success", "success $it")

                    it.data.data?.let { allData ->
                        (binding.recordGoalEmotionCardListRv.adapter as RemindContentsCardAdapter).submitList(
                            allData.content
                        )
                    }

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Log.d("failure", "failure $it")

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }
    }

    private fun moveToGoalFinishComment() {
        val action = RecordGoalFinishFragmentDirections.actionRecordGoalFinishFragmentToRecordGoalFinishCommentFragment(
            navArgs.goalData
        )

        findNavController().navigate(action)
    }
}