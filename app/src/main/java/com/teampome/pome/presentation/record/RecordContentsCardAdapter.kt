package com.teampome.pome.presentation.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding

class RecordContentsCardAdapter : ListAdapter<String, RecordContentsCardAdapter.RecordContentsCardViewHolder>(
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
        holder.bind()
    }

    inner class RecordContentsCardViewHolder(
        binding : ItemRecordEmotionCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }
}

class RecordContentsCardDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}