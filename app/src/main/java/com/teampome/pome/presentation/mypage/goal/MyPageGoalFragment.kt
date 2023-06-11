package com.teampome.pome.presentation.mypage.goal

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.databinding.FragmentMypageGoalBinding
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.mypage.MyPageGoalViewModel
import dagger.hilt.android.AndroidEntryPoint

// 마이페이지 완료된 목표 뷰

@AndroidEntryPoint
class MyPageGoalFragment : BaseFragment<FragmentMypageGoalBinding>(R.layout.fragment_mypage_goal) {

    private val viewModel: MyPageGoalViewModel by viewModels()

    override fun initView() {
        binding.viewModel = viewModel

        viewModel.findEndGoals(object: CoroutineErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun initListener() {
        viewModel.getEndGoalResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {}
                is ApiResponse.Success -> {
                    Log.d("test", "${it.data.data}")
                }
            }
        }

        // 뒤로가기
        binding.mypageGoalArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}