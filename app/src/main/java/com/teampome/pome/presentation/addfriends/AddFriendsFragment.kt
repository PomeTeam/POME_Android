package com.teampome.pome.presentation.addfriends

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.protobuf.Api
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddFriendsBinding
import com.teampome.pome.presentation.addfriends.recyclerview.AddFriendsListAdapter
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
        binding.addFriendsListRv.adapter = AddFriendsListAdapter()
    }

    override fun initListener() {

//        viewModel.testFriendsList.observe(viewLifecycleOwner) {
//            (binding.addFriendsListRv.adapter as AddFriendsListAdapter).submitList(it)
//        }

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
                    showLoading()

                    viewModel.findFriendsData(it.toString(), object : CoroutineErrorHandler {
                        override fun onError(message: String) {
                            Toast.makeText(requireContext(), "친구 찾기 중 서버 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            Log.e("findFriend", "findFriendError by $message")
                            hideLoading()
                        }
                    })
                }
            }
        })

        // find friends data Response
        viewModel.findFriendsDataResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("findFriend", "success by ${it.data}")

                    (binding.addFriendsListRv.adapter as AddFriendsListAdapter).submitList(it.data.data)

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Log.e("findFriend", "findFriendError by ${it.errorMessage}")
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                }
            }
        }

        binding.addFriendsCheckBtn.setOnClickListener {
            moveToRecordView()
        }

        // edittext x버튼 클릭
        binding.addFriendsFindDeleteAtv.setOnClickListener {
            binding.addFriendsNameEt.setText("")
        }
    }

    private fun moveToRecordView() {
        val action = AddFriendsFragmentDirections.actionAddFriendsFragmentToRecordFragment()
        findNavController().navigate(action)
    }
}