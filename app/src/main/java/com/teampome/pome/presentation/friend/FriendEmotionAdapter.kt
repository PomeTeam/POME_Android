package com.teampome.pome.presentation.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.R
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
            with(binding) {
                friendEmotionHappyTv.text = friendEmotion.nickname

                val imageResource = when(friendEmotion.emotionId) {
                    0 -> R.drawable.emotion_happy_54
                    1 -> R.drawable.emotion_what_54
                    2 -> R.drawable.emotion_sad_54
                    3 -> R.drawable.emotion_funny_54
                    4 -> R.drawable.emotion_smile_54
                    else -> R.drawable.emotion_flex_54
                }

                friendEmotionHappyAiv.setImageResource(imageResource)
            }
        }
    }
}