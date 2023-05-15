package com.teampome.pome.presentation.friend

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentFriendBinding
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.AddFriendsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendFragment : BaseFragment<FragmentFriendBinding>(R.layout.fragment_friend) {

    private val viewModel: AddFriendsViewModel by viewModels()

    private lateinit var friendGetAdapter: FriendGetAdapter
    private lateinit var friendRecordGetAdapter: FriendRecordGetAdapter

    override fun initView() {
        setUpRecyclerView()
        friendRecordSetUpRecyclerView()

        //친구 조회
        viewModel.getFriend(object : CoroutineErrorHandler{
            override fun onError(message: String) {
                Log.e("friendGetError", "friendGetError $message")
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }

    override fun initListener() {
        //친구 조회
        viewModel.getFriendsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    it.data.data?.let { list ->
                        (binding.friendListRv.adapter as FriendGetAdapter).submitList(
                            list
                        )
                    }
                }
                is ApiResponse.Failure -> {

                }
                is ApiResponse.Loading -> {}
            }
        }

        //친구 기록 조회
        viewModel.getFriendRecordResponse.observe(viewLifecycleOwner){
            when(it) {
                is ApiResponse.Success -> {
                    hideLoading()
                    it.data.data?.content?.let { list ->
                        (binding.friendDetailRv.adapter as FriendRecordGetAdapter).submitList(
                            list
                        )
                    }
                }
                is ApiResponse.Failure -> {

                }
                is ApiResponse.Loading -> {
                    showLoading()
                }
            }
        }
    }

    //친구 목록 조회 RV
    private fun setUpRecyclerView(){
        friendGetAdapter = FriendGetAdapter()
        binding.friendListRv.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = friendGetAdapter
        }

        // 클릭 리스너
        friendGetAdapter.setOnItemClickListener {
            viewModel.getRecordFriend(it.friendUserId, object : CoroutineErrorHandler{
                override fun onError(message: String) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
            })
        }
    }

    //친구 기록 조회 RV
    private fun friendRecordSetUpRecyclerView(){
        friendRecordGetAdapter = FriendRecordGetAdapter()
        binding.friendDetailRv.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = friendRecordGetAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }

        // 클릭 리스너
        friendRecordGetAdapter.setOnItemClickListener {
            Toast.makeText(requireContext(), "${it.nickname}", Toast.LENGTH_SHORT).show()
        }
    }
}