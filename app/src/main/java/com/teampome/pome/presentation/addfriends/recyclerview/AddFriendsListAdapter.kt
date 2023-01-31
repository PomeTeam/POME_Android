package com.teampome.pome.presentation.addfriends.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemAddFriendsListBinding
import com.teampome.pome.model.AddFriendsTestData
import com.teampome.pome.model.FriendData

class AddFriendsListAdapter : ListAdapter<FriendData, AddFriendsListAdapter.AddFriendsListViewHolder>(AddFriendsTestDataDiffCallback()) {

    private lateinit var binding: ItemAddFriendsListBinding

    private var onAddFriendClickListener: OnAddFriendClickListener? = null

    fun setOnAddFriendClickListener(addFriendClickListener: OnAddFriendClickListener) {
        onAddFriendClickListener = addFriendClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsListViewHolder {
        binding = ItemAddFriendsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AddFriendsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddFriendsListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AddFriendsListViewHolder(val binding: ItemAddFriendsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FriendData) {
            binding.friendData = item
            binding.executePendingBindings()

            binding.addFriendsListAddAiv.setOnClickListener {
                onAddFriendClickListener?.onAddFriendClick(item.friendUserId)

                binding.friendData = item.apply {
                    isAdd = true
                }
                binding.executePendingBindings()
            }
        }
    }
}

class AddFriendsTestDataDiffCallback : DiffUtil.ItemCallback<FriendData>() {
    override fun areItemsTheSame(
        oldItem: FriendData,
        newItem: FriendData
    ): Boolean {
        return oldItem.friendUserId == newItem.friendUserId
    }

    override fun areContentsTheSame(
        oldItem: FriendData,
        newItem: FriendData
    ): Boolean {
        return oldItem == newItem
    }
}