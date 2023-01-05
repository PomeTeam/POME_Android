package com.teampome.pome.presentation.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemTextListBinding

class TextListAdapter : ListAdapter<String, TextListAdapter.TextListViewHolder>(
    TextListDiffCallback()
) {
    private lateinit var binding: ItemTextListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextListViewHolder {
        binding = ItemTextListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TextListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TextListViewHolder(bind: ItemTextListBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(category: String) {
            binding.text = category
            binding.executePendingBindings()
        }
    }
}

class TextListDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}