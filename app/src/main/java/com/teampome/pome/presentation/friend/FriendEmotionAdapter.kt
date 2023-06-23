package com.teampome.pome.presentation.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemBottomEmojiBinding
import com.teampome.pome.model.response.FriendEmotionResponse

class FriendEmotionAdapter(
    private var friendEmotionList: List<FriendEmotionResponse>
) : RecyclerView.Adapter<FriendEmotionAdapter.FriendEmotionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendEmotionViewHolder {
        val binding = ItemBottomEmojiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendEmotionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendEmotionViewHolder, position: Int) {
        holder.bind(friendEmotionList[position])
    }

    override fun getItemCount(): Int = friendEmotionList.size

    fun updateData(newData: List<FriendEmotionResponse>) {
        this.friendEmotionList = newData
        notifyDataSetChanged()
    }

    inner class FriendEmotionViewHolder(
        private val binding: ItemBottomEmojiBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friendEmotion: FriendEmotionResponse) {
            binding.friendEmotionHappyTv.text = friendEmotion.nickname
        }
    }
}