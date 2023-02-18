package com.teampome.pome.presentation.remind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRemindContentsCardBinding
import com.teampome.pome.model.RecordData

class RemindContentsCardAdapter(

) : ListAdapter<RecordData, RemindContentsCardAdapter.RemindContentsCardViewHolder>(
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
        fun bind(item: RecordData) {
            binding.recordData = item
            binding.executePendingBindings()
        }
    }
}

class RemindContentsCardDiffCallback: DiffUtil.ItemCallback<RecordData>() {
    override fun areItemsTheSame(oldItem: RecordData, newItem: RecordData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecordData, newItem: RecordData): Boolean {
        return oldItem == newItem
    }
}