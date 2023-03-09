package com.teampome.pome.presentation.record.leave

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordLeaveEmotionBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.presentation.record.recyclerview.OnRecordItemClickListener
import com.teampome.pome.presentation.record.recyclerview.adapter.RecordContentsCardAdapter
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.record.RecordLeaveEmotionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordLeaveEmotionFragment : BaseFragment<FragmentRecordLeaveEmotionBinding>(R.layout.fragment_record_leave_emotion) {
    private val args: RecordLeaveEmotionFragmentArgs by navArgs()
    private val viewModel: RecordLeaveEmotionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("args", "args is $args")
    }

    override fun initView() {
        viewModel.getOneWeekRecordByGoalId(args.goalData.id, object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Log.e("getOneWeekRecord", "getOneWeekRecordError by $message")
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.goalData = args.goalData

        binding.recordLeaveEmotionRv.adapter = RecordContentsCardAdapter().apply {
            setOnRecordBodyClickListener(object : OnRecordItemClickListener {
                override fun onRecordItemClick(item: RecordData) {
                    item.id?.let {itemId ->
                        moveToLeaveEmotion(itemId)
                    }
                }
            })
        }

        binding.executePendingBindings()

        viewModel.getOneWeekRecordByGoalIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    binding.recordsCount = it.data.data?.content?.size ?: 0

                    binding.executePendingBindings()
                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Log.e("getOneWeekRecord", "getOneWeekRecord Failure by $it")
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }

//        viewModel.oneWeekRecords.observe(viewLifecycleOwner) {
//            (binding.recordLeaveEmotionRv.adapter as RecordContentsCardAdapter).submitList(
//                it
//            )
//        }
    }

    override fun initListener() {
        // 뒤로가기 클릭
        binding.recordLeaveEmotionBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun moveToLeaveEmotion(recordId: Int) {
        val action = RecordLeaveEmotionFragmentDirections.actionRecordLeaveEmotionFragmentToLeaveEmotionFragment(
            recordId
        )

        findNavController().navigate(action)
    }
}