package com.teampome.pome.presentation.friend

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentFriendBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeFriendEmotionBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeFriendSettingBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeRecordMoreGoalBottomSheetDialogBinding
import com.teampome.pome.model.response.FriendEmotionResponse
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.AddFriendsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendFragment : BaseFragment<FragmentFriendBinding>(R.layout.fragment_friend), FriendDetailRecordClickListener {

    private val viewModel: AddFriendsViewModel by viewModels()

    private lateinit var friendGetAdapter: FriendGetAdapter
    private lateinit var friendRecordGetAdapter: FriendRecordGetAdapter

    private lateinit var pomeFriendSettingBottomSheetDialogBinding : PomeFriendSettingBottomSheetDialogBinding
    private lateinit var friendSettingBottomSheetDialog: BottomSheetDialog

    private lateinit var pomeFriendEmojiBottomSheetDialogBinding : PomeFriendEmotionBottomSheetDialogBinding
    private lateinit var pomeFriendEmojiBottomSheetDialog: BottomSheetDialog

    override fun initView() {
        setUpRecyclerView()
        friendRecordSetUpRecyclerView()
        getAllFriendRecord()

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
                    hideLoading()
                    it.data.data?.let { list ->
                        if(list.isEmpty()) {
                            binding.friendNotIv.visibility = View.VISIBLE
                            binding.friendNotTv.visibility = View.VISIBLE
                        } else {
                            binding.friendNotIv.visibility = View.GONE
                            binding.friendNotTv.visibility = View.GONE
                            (binding.friendListRv.adapter as FriendGetAdapter).submitList(
                                list
                            )
                            friendRecordGetAdapter.updateFriendsMap(list)
                        }
                    }
                }
                is ApiResponse.Failure -> {
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                    showLoading()
                }
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
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                    showLoading()
                }
            }
        }

        viewModel.getAllFriendRecordResponse.observe(viewLifecycleOwner){
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
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                    showLoading()
                }
            }
        }

        viewModel.deleteFriendRecord.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    hideLoading()
                    Toast.makeText(requireContext(), "해당 게시글을 숨겼어요", Toast.LENGTH_SHORT).show()
                    viewModel.getAllRecordFriend(object : CoroutineErrorHandler{
                        override fun onError(message: String) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            hideLoading()
                        }
                    })
                }
                is ApiResponse.Failure -> {
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                    showLoading()
                }
            }
        }

        binding.friendAllIv.setOnClickListener {
            getAllFriendRecord()
        }

        binding.friendAddIv.setOnClickListener {
            val action = FriendFragmentDirections.actionFriendFragmentToAddFriendsFragment(true)
            findNavController().navigate(action)
        }
    }

    //친구 목록 조회 RV
    private fun setUpRecyclerView(){
        friendGetAdapter = FriendGetAdapter(context)
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
        friendRecordGetAdapter = FriendRecordGetAdapter(this)
        binding.friendDetailRv.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = friendRecordGetAdapter
        }
    }

    private fun getAllFriendRecord() {
        viewModel.getAllRecordFriend(object : CoroutineErrorHandler{
            override fun onError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }

    private fun makeFriendSettingBottomDialog(recordId: Int) {
        friendSettingBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeFriendSettingBottomSheetDialogBinding = PomeFriendSettingBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        friendSettingBottomSheetDialog.setContentView(pomeFriendSettingBottomSheetDialogBinding.root)

        pomeFriendSettingBottomSheetDialogBinding.apply{
            //숨기기
            pomeFriendBottomSheetDialogHideTv.setOnClickListener {
                viewModel.deleteFriendRecord(recordId, object : CoroutineErrorHandler{
                    override fun onError(message: String) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        hideLoading()
                    }
                })
                friendSettingBottomSheetDialog.dismiss()
            }

            //신고
            pomeFriendBottomSheetDialogReportTv.setOnClickListener {
                friendSettingBottomSheetDialog.dismiss()
            }
        }
    }

    private fun makeFriendEmojiBottomDialog(emojiList : List<FriendEmotionResponse>) {
        pomeFriendEmojiBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeFriendEmojiBottomSheetDialogBinding = PomeFriendEmotionBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        pomeFriendEmojiBottomSheetDialog.setContentView(pomeFriendEmojiBottomSheetDialogBinding.root)

        with(pomeFriendEmojiBottomSheetDialogBinding) {
            val emojiListSize = emojiList.size
            friendDetailAllEmotionTv.text = "전체 ${emojiListSize}개"

            val gridLayoutManager = GridLayoutManager(context, 3)
            val adapter = FriendEmotionAdapter(emojiList)
            val decoration = BottomEmojiGridSpaceItemDecoration(3)
            friendBottomEmojiRv.apply {
                layoutManager = gridLayoutManager
                this.adapter = adapter
                addItemDecoration(decoration)
            }
        }
    }

    override fun onFriendDetailMoreClick(recordId : Int) {
        makeFriendSettingBottomDialog(recordId)
        friendSettingBottomSheetDialog.show()
    }

    override fun onFriendDetailEmojiClick(emojiList: List<FriendEmotionResponse>) {
        makeFriendEmojiBottomDialog(emojiList)
        pomeFriendEmojiBottomSheetDialog.show()
    }
}