package com.teampome.pome.presentation.record.consume

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentConsumeCompleteBinding
import com.teampome.pome.util.base.BaseFragment

class ConsumeCompleteFragment : BaseFragment<FragmentConsumeCompleteBinding>(R.layout.fragment_consume_complete) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.consumeCompleteBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.consumeCompleteCheckButtonAcb.setOnClickListener {
            moveToRecord()
        }
    }

    private fun moveToRecord() {
        val action = ConsumeCompleteFragmentDirections.actionConsumeCompleteFragmentToRecordFragment()

        findNavController().navigate(action)
    }
}