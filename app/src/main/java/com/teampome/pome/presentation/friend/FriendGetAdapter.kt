package com.teampome.pome.presentation.friend

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teampome.pome.databinding.ItemFriendsListBinding
import com.teampome.pome.model.response.GetFriends

// 친구 탭에서 친구 조회하는 친구 리스트 어댑터
class FriendGetAdapter(
    private val context : Context?
) : ListAdapter<GetFriends, FriendGetAdapter.FriendGetViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendGetViewHolder {
        return FriendGetViewHolder(
            ItemFriendsListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: FriendGetViewHolder, position: Int) {
        val friends = currentList[position]
        holder.bind(friends)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let{ it(friends) }
        }
    }

    private var onItemClickListener : ((GetFriends) -> Unit)? = null
    fun setOnItemClickListener(listener : (GetFriends) -> Unit){
        onItemClickListener = listener
    }

    inner class FriendGetViewHolder(
        private val binding : ItemFriendsListBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(getFriends: GetFriends) = with(binding){
            friendAllTv.text = if (getFriends.friendNickName.length >= 3) {
                "${getFriends.friendNickName.substring(0, 3)}..."
            } else {
                getFriends.friendNickName
            }
            friendListProfileIv.clipToOutline = true

            context?.let { context ->
                Glide.with(context)
                    .load(getFriends.imageKey)
                    .circleCrop()
                    .into(friendListProfileIv)
            }
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