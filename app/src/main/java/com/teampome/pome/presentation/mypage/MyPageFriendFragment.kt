package com.teampome.pome.presentation.mypage

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentFriendDeleteDialogBinding
import com.teampome.pome.databinding.FragmentMypageFriendBinding
import com.teampome.pome.presentation.mypage.recyclerview.FriendDeleteAdapter
import com.teampome.pome.presentation.mypage.util.OnDeleteFriendClickListener
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.AddFriendsViewModel
import dagger.hilt.android.AndroidEntryPoint

//마이페이지 친구관리 뷰
@AndroidEntryPoint
class MyPageFriendFragment : BaseFragment<FragmentMypageFriendBinding>(R.layout.fragment_mypage_friend) {

    private val viewModel: AddFriendsViewModel by viewModels()

    private lateinit var friendDeleteAdapter: FriendDeleteAdapter

    //친구 삭제 다이얼로그
    private lateinit var friendDeleteDialogBinding : FragmentFriendDeleteDialogBinding
    private lateinit var friendDeleteDialog : Dialog

    override fun initView() {
        setUpRecyclerView()

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
                        (binding.mypageFriendRv.adapter as FriendDeleteAdapter).submitList(
                            list
                        )
                    }
                }
                is ApiResponse.Failure -> {

                }
                is ApiResponse.Loading -> {}
            }
        }
        //뒤로가기
        binding.mypageFriendArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView(){
        friendDeleteAdapter = FriendDeleteAdapter().apply {
            setOnDeleteFriendClickListener(object : OnDeleteFriendClickListener {
                override fun onDeleteFriendClick(friendId: String, position: Int) {
                    friendDeleteDialog(friendId)
                    //친구 삭제 다이얼로그
                    friendDeleteDialog.show()
                }
            })
        }
        binding.mypageFriendRv.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = friendDeleteAdapter
        }
    }

    private fun friendDeleteDialog(friendId : String) {
        friendDeleteDialogBinding = FragmentFriendDeleteDialogBinding.inflate(layoutInflater, null, false)

        friendDeleteDialog = Dialog(requireContext())
        friendDeleteDialog.setContentView(friendDeleteDialogBinding.root)

        CommonUtil.makePomeDialog(friendDeleteDialog)

        friendDeleteDialogBinding.friendDeleteOkayTv.setOnClickListener {
            viewModel.deleteFriend(friendId, object : CoroutineErrorHandler{
                override fun onError(message: String) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            })

            friendDeleteDialog.dismiss()
        }


        friendDeleteDialogBinding.friendDeleteNotAllTv.setOnClickListener {
            friendDeleteDialog.dismiss()
        }
    }
}