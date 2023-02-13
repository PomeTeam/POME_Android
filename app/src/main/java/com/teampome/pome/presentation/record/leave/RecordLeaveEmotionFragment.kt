package com.teampome.pome.presentation.record.leave

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordLeaveEmotionBinding
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
    }

    override fun initListener() {
        // 뒤로가기 클릭
        binding.recordLeaveEmotionBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}