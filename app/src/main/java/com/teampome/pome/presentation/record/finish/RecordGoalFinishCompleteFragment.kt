package com.teampome.pome.presentation.record.finish

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRecordGoalFinishCompleteBinding
import com.teampome.pome.util.base.BaseFragment

class RecordGoalFinishCompleteFragment : BaseFragment<FragmentRecordGoalFinishCompleteBinding>(R.layout.fragment_record_goal_finish_complete) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        settingBackPressedCallback(object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {

            }
        })
    }

    override fun initListener() {
        binding.goalFinishCompleteCheckButtonAcb.setOnClickListener {
            moveToRecordView()
        }
    }

    private fun moveToRecordView() {
        val action = RecordGoalFinishCompleteFragmentDirections.actionRecordGoalFinishCompleteFragmentToRecordFragment()

        findNavController().navigate(action)
    }
}