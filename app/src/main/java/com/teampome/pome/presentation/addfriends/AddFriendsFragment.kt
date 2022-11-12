package com.teampome.pome.presentation.addfriends

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddFriendsBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
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

    override fun initListener() {

        viewModel.testFriendsList.observe(viewLifecycleOwner) {
            Log.d("testData", "test data $it")
        }

        binding.addFriendsCl.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
        }

        binding.addFriendsNameEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(name: Editable?) {

            }
        })
    }
}