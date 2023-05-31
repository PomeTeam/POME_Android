package com.teampome.pome.presentation.mypage.recyclerview.main

import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemMypageUserViewBinding

class MyPageUserViewHolder(
    private val binding: ItemMypageUserViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userName: String, userProfileUrl: String) {
        binding.userName = userName
        binding.userProfileUrl = userProfileUrl

        binding.executePendingBindings()
    }
}