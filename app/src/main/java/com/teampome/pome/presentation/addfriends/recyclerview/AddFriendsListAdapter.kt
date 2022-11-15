package com.teampome.pome.presentation.addfriends.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemAddFriendsListBinding
import com.teampome.pome.model.AddFriendsTestData

class AddFriendsListAdapter : ListAdapter<AddFriendsTestData, AddFriendsListAdapter.AddFriendsListViewHolder>(AddFriendsTestDataDiffCallback()) {

    private lateinit var binding: ItemAddFriendsListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsListViewHolder {
        binding = ItemAddFriendsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AddFriendsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddFriendsListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AddFriendsListViewHolder(val binding: ItemAddFriendsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AddFriendsTestData) {
            binding.addFriendsTestData = item
            binding.executePendingBindings()
        }
    }
}

class AddFriendsTestDataDiffCallback() : DiffUtil.ItemCallback<AddFriendsTestData>() {
    override fun areItemsTheSame(
        oldItem: AddFriendsTestData,
        newItem: AddFriendsTestData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AddFriendsTestData,
        newItem: AddFriendsTestData
    ): Boolean {
        return oldItem == newItem
    }

}