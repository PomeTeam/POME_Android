package com.teampome.pome.presentation.mypage.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemDeleteFriendsListBinding
import com.teampome.pome.model.response.GetFriends
import com.teampome.pome.presentation.mypage.util.OnDeleteFriendClickListener

// 마이탭에서 친구 삭제 리스트 어댑터
class FriendDeleteAdapter(
    val onDeleteClick: (String) -> Unit
) : ListAdapter<GetFriends, FriendDeleteAdapter.FriendDeleteViewHolder>(
    BookDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendDeleteViewHolder {
        return FriendDeleteViewHolder(
            ItemDeleteFriendsListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: FriendDeleteViewHolder, position: Int) {
        val friends = currentList[position]

        holder.bind(friends)
    }

    inner class FriendDeleteViewHolder(
        private val binding : ItemDeleteFriendsListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(getFriends: GetFriends){
            binding.getFriends = getFriends

            binding.addFriendsListDeleteAiv.setOnClickListener {
                onDeleteClick(getFriends.friendNickName)
            }
        }

    }
    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<GetFriends>(){
            override fun areItemsTheSame(oldItem: GetFriends, newItem: GetFriends): Boolean {
                return oldItem.friendUserId == newItem.friendUserId
            }

            override fun areContentsTheSame(oldItem: GetFriends, newItem: GetFriends): Boolean {
                return oldItem == newItem
            }
        }
    }
}