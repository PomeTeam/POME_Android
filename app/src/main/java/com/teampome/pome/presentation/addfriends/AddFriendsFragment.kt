package com.teampome.pome.presentation.addfriends

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CommonUtil.inputModePan(requireActivity())
    }

    override fun initView() {
        binding.addFriendsListRv.adapter = AddFriendsListAdapter().apply {
            setOnAddFriendClickListener(object : OnAddFriendClickListener {
                override fun onAddFriendClick(friendId: String) {
                    showLoading()

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
                    binding.friendData = it.data.data
                    binding.executePendingBindings()

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
                    Log.d("friend", "친구추가 완료 $it")
                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                }
            }
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
        binding.friendData = listOf()
        binding.executePendingBindings()

        (binding.addFriendsListRv.adapter as AddFriendsListAdapter).submitList(listOf())
    }

    private fun moveToRecordView() {
        val action = AddFriendsFragmentDirections.actionAddFriendsFragmentToRecordFragment()
        findNavController().navigate(action)
    }
}