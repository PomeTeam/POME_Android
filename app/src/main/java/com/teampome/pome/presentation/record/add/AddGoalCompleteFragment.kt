package com.teampome.pome.presentation.record.add

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddGoalCompleteBinding
import com.teampome.pome.util.base.BaseFragment

class AddGoalCompleteFragment : BaseFragment<FragmentAddGoalCompleteBinding>(R.layout.fragment_add_goal_complete) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.addGoalCompleteCheckButtonAcb.setOnClickListener {
            moveToRecord()
        }
    }

    private fun moveToRecord() {
        val action = AddGoalCompleteFragmentDirections.actionAddGoalCompleteFragmentToRecordFragment()

        findNavController().navigate(action)
    }
}