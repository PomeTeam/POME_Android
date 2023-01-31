package com.teampome.pome.presentation.addfriends

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddFriendsBinding
import com.teampome.pome.presentation.addfriends.recyclerview.AddFriendsListAdapter
import com.teampome.pome.presentation.addfriends.recyclerview.OnAddFriendClickListener
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.AddFriendsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFriendsFragment : BaseFragment<FragmentAddFriendsBinding>(R.layout.fragment_add_friends) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private val viewModel: AddFriendsViewModel by viewModels()

    private var checkPos: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CommonUtil.inputModePan(requireActivity())
    }

    override fun initView() {
        binding.addFriendsListRv.adapter = AddFriendsListAdapter().apply {
            setOnAddFriendClickListener(object : OnAddFriendClickListener {
                override fun onAddFriendClick(friendId: String, position: Int) {
                    showLoading()

                    checkPos = position
                    Log.d("friend", "on Click $friendId")

                    viewModel.addFriend(friendId, object : CoroutineErrorHandler {
                        override fun onError(message: String) {
                            Log.e("friend", message)
                            hideLoading()
                        }
                    })
                }
            })
        }
    }

    override fun initListener() {

        binding.addFriendsCl.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
        }

        binding.addFriendsNameEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(name: Editable?) {
                name?.let {
                    viewModel.findText.value = it.toString()
                }
            }
        })

        // find friends data Response
        viewModel.findFriendsDataResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("findFriend", "success by ${it.data}")

                    (binding.addFriendsListRv.adapter as AddFriendsListAdapter).submitList(it.data.data)
                    viewModel.settingFriendsData(it.data.data)

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    if(it.code != 400) {
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                        Log.e("findFriend", "findFriendFailure by ${it.errorMessage}")
                    }
                    bindEmptyFriendData()
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                }
            }
        }

        // 친구 추가 관련 api
        viewModel.addFriendResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {

                    // U0004 -> 이미 팔로우한 유저 에러, 일단 추가완료 처리..
                    if(it.data.success || it.data.errorCode == "U0004") {
                        if(it.data.errorCode == "U0004") {
                            Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                        }

                        viewModel.settingFriendsData(
                            viewModel.friendsData.value.apply {
                                this?.get(checkPos)?.isAdd = true
                            }
                        )
                    }

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                }
            }
        }

        viewModel.friendsData.observe(viewLifecycleOwner) {
            Log.d("friendsData", "observe $it")

            (binding.addFriendsListRv.adapter as AddFriendsListAdapter).submitList(it)
            (binding.addFriendsListRv.adapter as AddFriendsListAdapter).notifyItemChanged(checkPos)

            binding.friendData = it
            binding.executePendingBindings()
        }

        binding.addFriendsCheckBtn.setOnClickListener {
            moveToRecordView()
        }

        // 검색버튼 클릭
        binding.addFriendsNameSearchAiv.setOnClickListener {
            showLoading()

            viewModel.findText.value?.let {
                viewModel.findFriendsData(it, object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Log.e("findFriend", "findFriendError by $message")

                        bindEmptyFriendData()
                        hideLoading()
                    }
                })
            } ?: run {
                bindEmptyFriendData()
                hideLoading()
            }
        }

        // edittext x버튼 클릭
        binding.addFriendsFindDeleteAtv.setOnClickListener {
            binding.addFriendsNameEt.setText("")
        }
    }

    private fun bindEmptyFriendData() {
        viewModel.settingFriendsData(null)
    }

    private fun moveToRecordView() {
        val action = AddFriendsFragmentDirections.actionAddFriendsFragmentToRecordFragment()
        findNavController().navigate(action)
    }
}