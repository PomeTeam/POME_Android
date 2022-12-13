package com.teampome.pome.presentation.remind

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.R
import com.teampome.pome.databinding.ItemRemindCategoryChipBinding

class RemindCategoryChipAdapter(
    val resources: Resources,
    ) : ListAdapter<String, RemindCategoryChipAdapter.RemindCategoryChipViewHolder>(
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
        holder.bind(getItem(position), position)
    }

    inner class RemindCategoryChipViewHolder(
        val binding : ItemRemindCategoryChipBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chipText: String, position: Int) {
            if(position == 0) { // 첫 데이터는 활성화 상태로
                activeCategory(binding.remindCategoryChipTv)
            }

            binding.testCategory = chipText

            binding.remindCategoryChipTv.setOnClickListener {
                activeCategory(it as TextView)
            }

            binding.executePendingBindings()
        }
    }

    private fun activeCategory(textView: TextView) {
        textView.setBackgroundResource(R.drawable.remind_chip_background)
        textView.setTextColor(resources.getColor(R.color.white, null))
    }

    private fun deActiveCategory(textView: TextView) {
        textView.background = null
        textView.setTextColor(resources.getColor(R.color.grey_5, null))
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