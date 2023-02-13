package com.teampome.pome.presentation.record

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordAlarmsBinding
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.record.RecordAlarmsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordAlarmsFragment : BaseFragment<FragmentRecordAlarmsBinding>(R.layout.fragment_record_alarms) {
    private val viewModel: RecordAlarmsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        binding.recordAlarmsRv.adapter = RecordAlarmsAdapter()
    }

    override fun initListener() {
        viewModel.testAlarmsData.observe(viewLifecycleOwner) {
            (binding.recordAlarmsRv.adapter as RecordAlarmsAdapter).submitList(it)
        }

        // 뒤로가기 버튼 클릭
        binding.recordAlarmsBackButtonAiv.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}