package com.teampome.pome.presentation.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.presentation.mypage.recyclerview.main.MyPageViewAdapter
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 탭
/*
    RecyclerView의 여러 ViewType에 중첩 RecyclerView 구조 (전체 스크롤 구현)
 */
@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageViewModel by viewModels()

    private lateinit var myPageViewAdapter: MyPageViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun initView() {
        viewModel.getPastGoals(object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun initListener() {
        // observe 하는 상황에서 모든 data들이 update된 후에 recyclerView setting
        viewModel.pastGoals.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    // 확실하게 순서 보장, pastGoals 넣고 마지막에 marshmellow data가 담길 수 있게
                    viewModel.getMarshmello(object : CoroutineErrorHandler{
                        override fun onError(message: String) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            hideLoading()
                        }
                    })
                }
                is ApiResponse.Failure -> {}
                is ApiResponse.Loading -> {}
            }
        }

        viewModel.pastGoalsCnt.observe(viewLifecycleOwner) {}

        viewModel.getMarshmello.observe(viewLifecycleOwner) {}

        viewModel.myPageViewDataList.observe(viewLifecycleOwner) { dataList ->
            myPageViewAdapter = MyPageViewAdapter(
                ::moveToSettingView,
                ::moveToGoalSettingView
            ).apply {
                addPageDataList(dataList)
            }

            binding.mypageViewRv.adapter = myPageViewAdapter
        }

        //설정화면으로
//        binding.mypageSettingIv.setOnClickListener {
//            val action = MyPageFragmentDirections.actionMypageFragmentToMyPageSettingFragment()
//            findNavController().navigate(action)
//        }

        //목표설정화면으로
//        binding.mypageMainCl.setOnClickListener {
//            val action = MyPageFragmentDirections.actionMypageFragmentToMyPageGoalFragment()
//            findNavController().navigate(action)
//        }
    }

    private fun moveToSettingView() {
        val action = MyPageFragmentDirections.actionMypageFragmentToMyPageSettingFragment()
        findNavController().navigate(action)
    }

    private fun moveToGoalSettingView() {
        val action = MyPageFragmentDirections.actionMypageFragmentToMyPageGoalFragment()
        findNavController().navigate(action)
    }
}