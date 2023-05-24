package com.teampome.pome.presentation.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentMypageBinding
import com.teampome.pome.presentation.mypage.recyclerview.GridSpaceItemDecoration
import com.teampome.pome.presentation.mypage.recyclerview.MarshmelloAdapter
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 탭
@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageViewModel by viewModels()

    private lateinit var marshmelloAdapter: MarshmelloAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    override fun initView() {
        setUpRecyclerView()

        viewModel.getPastGoals(object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getMarshmello(object : CoroutineErrorHandler{
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }

    override fun initListener() {
        viewModel.pastGoals.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {}
                is ApiResponse.Failure -> {}
                is ApiResponse.Loading -> {}
            }
        }

        viewModel.pastGoalsCnt.observe(viewLifecycleOwner) {}

        viewModel.getMarshmello.observe(viewLifecycleOwner){
            when(it) {
                is ApiResponse.Success -> {
                    it.data.data?.let {
                        marshmelloAdapter.submitList(it)
                    }
                }
                is ApiResponse.Failure -> {

                }
                is ApiResponse.Loading -> {}
            }
        }

        //설정화면으로
        binding.mypageSettingIv.setOnClickListener {
            val action = MyPageFragmentDirections.actionMypageFragmentToMyPageSettingFragment()
            findNavController().navigate(action)
        }

        //목표설정화면으로
        binding.mypageMainCl.setOnClickListener {
            val action = MyPageFragmentDirections.actionMypageFragmentToMyPageGoalFragment()
            findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerView(){
        marshmelloAdapter = MarshmelloAdapter(requireContext())
        val manager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)

        //첫 번째 나오는 Header size 를 꽉 차게 함
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = 1
        }
        binding.recordEmotionRv.apply {
//            setHasFixedSize(true)
            layoutManager = manager
            adapter = marshmelloAdapter
            addItemDecoration(GridSpaceItemDecoration(2))
        }
    }
}