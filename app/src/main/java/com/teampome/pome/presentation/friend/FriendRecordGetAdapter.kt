package com.teampome.pome.presentation.friend

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teampome.pome.R
import com.teampome.pome.databinding.ItemFriendDetailCardBinding
import com.teampome.pome.model.response.GetFriendRecord
import com.teampome.pome.model.response.GetFriends
import java.time.Duration
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

//친구 기록 조회
class FriendRecordGetAdapter(
    private val clickListener: FriendDetailRecordClickListener,
) : ListAdapter<GetFriendRecord, FriendGetRecordViewHolder>(BookDiffCallback) {

    private val friendsMap: HashMap<String, GetFriends> = HashMap()

    fun updateFriendsMap(friends: List<GetFriends>) {
        for (friend in friends) {
            friendsMap[friend.friendNickName] = friend
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendGetRecordViewHolder {
        return FriendGetRecordViewHolder(
            clickListener,
            ItemFriendDetailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            friendsMap
        )
    }

    override fun onBindViewHolder(holder: FriendGetRecordViewHolder, position: Int) {
        val friends = currentList[position]
        holder.bind(friends)
    }

    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<GetFriendRecord>(){
            override fun areItemsTheSame(oldItem: GetFriendRecord, newItem: GetFriendRecord): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GetFriendRecord, newItem: GetFriendRecord): Boolean {
                return oldItem == newItem
            }
        }
    }
}