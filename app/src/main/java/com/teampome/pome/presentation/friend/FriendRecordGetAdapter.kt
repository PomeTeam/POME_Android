package com.teampome.pome.presentation.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemFriendDetailCardBinding
import com.teampome.pome.databinding.ItemFriendsListBinding
import com.teampome.pome.model.response.GetFriendRecord
import com.teampome.pome.model.response.GetFriends

//친구 기록 조회
class FriendRecordGetAdapter : ListAdapter<GetFriendRecord, FriendRecordGetAdapter.FriendGetRecordViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendGetRecordViewHolder {
        return FriendGetRecordViewHolder(
            ItemFriendDetailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: FriendGetRecordViewHolder, position: Int) {
        val friends = currentList[position]
        holder.bind(friends)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let{ it(friends) }
        }
    }

    private var onItemClickListener : ((GetFriendRecord) -> Unit)? = null
    fun setOnItemClickListener(listener : (GetFriendRecord) -> Unit){
        onItemClickListener = listener
    }

    inner class FriendGetRecordViewHolder(
        private val binding : ItemFriendDetailCardBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(getFriedRecord: GetFriendRecord){
            binding.getFriendRecord = getFriedRecord
        }

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