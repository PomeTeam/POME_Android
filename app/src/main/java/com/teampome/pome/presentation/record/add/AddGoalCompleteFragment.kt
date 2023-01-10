package com.teampome.pome.presentation.record.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddGoalCompleteBinding
import com.teampome.pome.util.base.BaseFragment

class AddGoalCompleteFragment : BaseFragment<FragmentAddGoalCompleteBinding>(R.layout.fragment_add_goal_complete) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
        binding.addGoalCompleteCheckButtonAcb.setOnClickListener {
            Toast.makeText(requireContext(), "확인했어요", Toast.LENGTH_SHORT).show()
        }
    }
}