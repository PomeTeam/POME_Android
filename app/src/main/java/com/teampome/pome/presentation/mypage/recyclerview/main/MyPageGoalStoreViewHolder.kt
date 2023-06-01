package com.teampome.pome.presentation.mypage.recyclerview.main

import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemMypageGoalStoreViewBinding

class MyPageGoalStoreViewHolder(
    private val binding: ItemMypageGoalStoreViewBinding,
    private val onGoalSettingClick: () -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(goalCnt: Int) {
        binding.goalCnt = goalCnt
        binding.mypageMainCl.setOnClickListener {
            onGoalSettingClick.invoke()
        }

        binding.executePendingBindings()
    }
}