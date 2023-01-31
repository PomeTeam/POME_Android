package com.teampome.pome.presentation.addfriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddFriendsNoticeBinding
import com.teampome.pome.util.base.BaseFragment

class AddFriendsNoticeFragment : BaseFragment<FragmentAddFriendsNoticeBinding>(R.layout.fragment_add_friends_notice) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.addFriendsNoticeCheckFalseAtv.setOnClickListener {
            moveToRecord()
        }

        binding.addFriendsNoticeCheckBtn.setOnClickListener {
            moveToAddFriends()
        }
    }

    private fun moveToRecord() {
        val addFriendsNoticeToRecord = AddFriendsNoticeFragmentDirections.actionAddFriendsNoticeFragmentToRecordFragment()
        findNavController().navigate(addFriendsNoticeToRecord)
    }

    private fun moveToAddFriends() {
        val addFriendsNoticeToAddFriends = AddFriendsNoticeFragmentDirections.actionAddFriendsNoticeFragmentToAddFriendsFragment()
        findNavController().navigate(addFriendsNoticeToAddFriends)
    }
}