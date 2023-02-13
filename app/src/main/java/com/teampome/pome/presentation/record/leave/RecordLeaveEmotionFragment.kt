package com.teampome.pome.presentation.record.leave

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordLeaveEmotionBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.presentation.record.OnRecordItemClickListener
import com.teampome.pome.presentation.record.RecordContentsCardAdapter
import com.teampome.pome.util.OnItemClickListener
import com.teampome.pome.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordLeaveEmotionFragment : BaseFragment<FragmentRecordLeaveEmotionBinding>(R.layout.fragment_record_leave_emotion) {
    private val args: RecordLeaveEmotionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("args", "args is $args")
    }

    override fun initView() {
        binding.goalData = args.goalData
        binding.recordsCount = args.recordList.size

        binding.recordLeaveEmotionRv.adapter = RecordContentsCardAdapter().apply {
            setOnBodyClickListener(object : OnRecordItemClickListener {
                override fun onRecordItemClick(item: RecordData) {
                    moveToLeaveEmotion(item.id)
                }
            })
            submitList(args.recordList.asList())
        }

        binding.executePendingBindings()
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