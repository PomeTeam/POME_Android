package com.teampome.pome.presentation.mypage.setting

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentFriendDeleteDialogBinding
import com.teampome.pome.databinding.FragmentMypageFriendBinding
import com.teampome.pome.presentation.mypage.recyclerview.FriendDeleteAdapter
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.mypage.MyPageFriendViewModel
import dagger.hilt.android.AndroidEntryPoint

// 마이페이지 친구관리 뷰
@AndroidEntryPoint
class MyPageFriendFragment : BaseFragment<FragmentMypageFriendBinding>(R.layout.fragment_mypage_friend) {

    private val viewModel: MyPageFriendViewModel by viewModels()

    private lateinit var friendDeleteAdapter: FriendDeleteAdapter

    // 친구 삭제 다이얼로그
    private lateinit var friendDeleteDialogBinding : FragmentFriendDeleteDialogBinding
    private lateinit var friendDeleteDialog : Dialog

    override fun initView() {
        makeFriendDeleteDialog()
        setRecyclerView()

        requestApi()
    }

    override fun initListener() {
        viewModel.getFriendsResponse.observe(viewLifecycleOwner) { apiResponse ->
            when(apiResponse) {
                is ApiResponse.Loading -> { showLoading() }
                is ApiResponse.Failure -> { hideLoading() }
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { friendList ->
                        Log.d("data", "is in? $friendList")

                        friendDeleteAdapter.submitList(friendList)
                    }

                    hideLoading()
                }
            }
        }

        viewModel.deleteFriendResponse.observe(viewLifecycleOwner) { apiResponse ->
            when(apiResponse) {
                is ApiResponse.Loading -> { showLoading() }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), apiResponse.errorMessage, Toast.LENGTH_SHORT).show()
                    hideLoading()
                    friendDeleteDialog.dismiss()
                }
                is ApiResponse.Success -> {
                    hideLoading()
                    friendDeleteDialog.dismiss()

                    // view 초기화 필요
                    requestApi()
                }
            }
        }

        // 뒤로가기
        binding.mypageFriendArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun requestApi() {
        viewModel.getFriend(object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Toast.makeText(requireContext(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }

    private fun setRecyclerView() {
        friendDeleteAdapter = FriendDeleteAdapter { friendId ->
            setFriendDeleteDialogOkMethod {
                viewModel.deleteFriend(friendId, object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        hideLoading()
                    }
                })
            }

            friendDeleteDialog.show()
        }

        binding.mypageFriendRv.adapter = friendDeleteAdapter
    }

    private fun makeFriendDeleteDialog() {
        friendDeleteDialogBinding = FragmentFriendDeleteDialogBinding.inflate(layoutInflater, null, false)

        friendDeleteDialog = Dialog(requireContext())
        friendDeleteDialog.setContentView(friendDeleteDialogBinding.root)

        CommonUtil.makePomeDialog(friendDeleteDialog)

        friendDeleteDialogBinding.friendDeleteNotAllTv.setOnClickListener {
            friendDeleteDialog.dismiss()
        }
    }

    private fun setFriendDeleteDialogOkMethod(onDeleteClick : () -> Unit) {
        friendDeleteDialogBinding.friendDeleteOkayTv.setOnClickListener {
            onDeleteClick.invoke()
        }
    }
}