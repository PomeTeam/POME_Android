package com.teampome.pome.presentation.remind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRemindContentsCardBinding
import com.teampome.pome.model.ContentCardItem
import com.teampome.pome.util.CommonUtil.getEmotionData
import com.teampome.pome.util.Constants
import com.teampome.pome.viewmodel.Emotion

class RemindContentsCardAdapter(
    private val remindItemClickListener: RemindItemClickListener? = null
) : ListAdapter<ContentCardItem, RemindContentsCardAdapter.RemindContentsCardViewHolder>(
    RemindContentsCardDiffCallback()
) {

    private lateinit var binding: ItemRemindContentsCardBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RemindContentsCardViewHolder {
        binding = ItemRemindContentsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RemindContentsCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindContentsCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RemindContentsCardViewHolder(
        val binding: ItemRemindContentsCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentCardItem) {
            binding.contentCardItem = item
            binding.firstEmotion = getEmotionData(item.firstThink)
            binding.lastEmotion = getEmotionData(item.lastThink)
            binding.clickListener = remindItemClickListener
            binding.executePendingBindings()
        }
    }
}

class RemindContentsCardDiffCallback: DiffUtil.ItemCallback<ContentCardItem>() {
    override fun areItemsTheSame(oldItem: ContentCardItem, newItem: ContentCardItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ContentCardItem, newItem: ContentCardItem): Boolean {
        return oldItem == newItem
    }
}