package com.teampome.pome.presentation.record.consume

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentConsumeRecordBinding
import com.teampome.pome.util.base.BaseFragment

class ConsumeRecordFragment : BaseFragment<FragmentConsumeRecordBinding>(R.layout.fragment_consume_record) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
        binding.consumeRecordBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}