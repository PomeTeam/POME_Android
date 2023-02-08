package com.teampome.pome.presentation.record.leave

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordLeaveEmotionBinding
import com.teampome.pome.util.base.BaseFragment

class RecordLeaveEmotionFragment : BaseFragment<FragmentRecordLeaveEmotionBinding>(R.layout.fragment_record_leave_emotion) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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