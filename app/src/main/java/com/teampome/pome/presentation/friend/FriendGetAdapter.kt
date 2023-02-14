package com.teampome.pome.presentation.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemFriendsListBinding
import com.teampome.pome.model.response.GetFriends

// 친구 탭에서 친구 조회하는 친구 리스트 어댑터
class FriendGetAdapter : ListAdapter<GetFriends, FriendGetAdapter.FriendGetViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendGetViewHolder {
        return FriendGetViewHolder(
            ItemFriendsListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: FriendGetViewHolder, position: Int) {
        val foodResult = currentList[position]
        holder.bind(foodResult)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let{ it(foodResult) }
        }
    }

    private var onItemClickListener : ((GetFriends) -> Unit)? = null
    fun setOnItemClickListener(listener : (GetFriends) -> Unit){
        onItemClickListener = listener
    }

    inner class FriendGetViewHolder(
        private val binding : ItemFriendsListBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(getFriends: GetFriends){
            binding.getFriends = getFriends

            submitList(currentList)
        }

    }
    companion object{
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