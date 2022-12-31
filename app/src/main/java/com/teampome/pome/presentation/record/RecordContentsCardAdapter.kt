package com.teampome.pome.presentation.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding
import com.teampome.pome.model.RecordWeekItem
import com.teampome.pome.util.CommonUtil

class RecordContentsCardAdapter : ListAdapter<RecordWeekItem, RecordContentsCardAdapter.RecordContentsCardViewHolder>(
    RecordContentsCardDiffCallback()
) {
    private lateinit var binding: ItemRecordEmotionCardBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordContentsCardViewHolder {
        binding = ItemRecordEmotionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecordContentsCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordContentsCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecordContentsCardViewHolder(
        binding : ItemRecordEmotionCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecordWeekItem) {
            binding.recordWeekItem = item
            binding.firstEmotion = CommonUtil.getEmotionData(item.firstThink)
            binding.lastEmotion = item.lastThink?.let { CommonUtil.getEmotionData(it) }
            binding.executePendingBindings()
        }
    }
}

class RecordContentsCardDiffCallback : DiffUtil.ItemCallback<RecordWeekItem>() {
    override fun areItemsTheSame(oldItem: RecordWeekItem, newItem: RecordWeekItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RecordWeekItem, newItem: RecordWeekItem): Boolean {
        return oldItem == newItem
    }
}