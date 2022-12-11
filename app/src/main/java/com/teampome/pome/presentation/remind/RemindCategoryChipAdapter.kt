package com.teampome.pome.presentation.remind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRemindCategoryChipBinding

class RemindCategoryChipAdapter : ListAdapter<String, RemindCategoryChipAdapter.RemindCategoryChipViewHolder>(
    RemindCategoryDiffCallback()
) {
    private lateinit var binding: ItemRemindCategoryChipBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RemindCategoryChipViewHolder {
        binding = ItemRemindCategoryChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RemindCategoryChipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindCategoryChipViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RemindCategoryChipViewHolder(
        val binding : ItemRemindCategoryChipBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chipText: String) {
            binding.testCategory = chipText
            binding.executePendingBindings()
        }
    }
}

// 왜 상속인데 인스턴스를 넣는거지? => 인스턴스를 상속해야 부모 클래스의 구현체들을 이용할 수 있을 것 같음
class RemindCategoryDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}