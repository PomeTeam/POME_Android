package com.teampome.pome.presentation.friend

import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentFriendBinding
import com.teampome.pome.presentation.record.RecordCategoryAdapter
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.AddFriendsViewModel
import com.teampome.pome.viewmodel.record.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendFragment : BaseFragment<FragmentFriendBinding>(R.layout.fragment_friend) {

    private val viewModel: AddFriendsViewModel by viewModels()
    private lateinit var friendGetAdapter: FriendGetAdapter

    override fun initView() {
        setUpRecyclerView()

        //친구 조회
        viewModel.getFriend(object : CoroutineErrorHandler{
            override fun onError(message: String) {
                Log.e("record", "record error $message")
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }

    override fun initListener() {
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
    }

    private fun setUpRecyclerView(){
        friendGetAdapter = FriendGetAdapter()
        binding.friendListRv.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = friendGetAdapter
        }

        // 클릭 리스너
        friendGetAdapter.setOnItemClickListener {
            Toast.makeText(requireContext(), "${it.friendNickName}", Toast.LENGTH_SHORT).show()
        }

    }
}