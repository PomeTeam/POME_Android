package com.teampome.pome.presentation.mypage.recyclerview.main

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemMypageMarshmallowContentViewBinding
import com.teampome.pome.model.mytab.MyTabMarshmello
import com.teampome.pome.presentation.mypage.recyclerview.MarshmelloAdapter

class MyPageMarshMallowContentViewHolder(
    private val binding: ItemMypageMarshmallowContentViewBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(marshMallowList: List<MyTabMarshmello>?) {
        (binding.recordEmotionRv.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = 1
        }

        binding.recordEmotionRv.adapter = MarshmelloAdapter().apply {
            submitList(marshMallowList)
        }

        binding.executePendingBindings()
    }
}