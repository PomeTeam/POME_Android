package com.teampome.pome.presentation.friend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teampome.pome.R
import com.teampome.pome.databinding.ItemFriendDetailCardBinding
import com.teampome.pome.model.response.GetFriendRecord

//친구 기록 조회
class FriendRecordGetAdapter(
    private val clickListener: FriendDetailRecordClickListener,
    private val context : Context?
) : ListAdapter<GetFriendRecord, FriendRecordGetAdapter.FriendGetRecordViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendGetRecordViewHolder {
        return FriendGetRecordViewHolder(
            clickListener,
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
        private val friendDetailRecordClickListener: FriendDetailRecordClickListener,
        private val binding : ItemFriendDetailCardBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(getFriedRecord: GetFriendRecord){
            with(binding) {
                getFriendRecord = getFriedRecord

                friendDetailMoreSettingIv.setOnClickListener {
                    friendDetailRecordClickListener.onFriendDetailMoreClick(getFriedRecord.id)
                }

                //Todo 시간 표시 필요
                friendDetailContentNameTimeTv.text = " · ${getFriedRecord.oneLineMind}"

                if(getFriedRecord.emotionResponse.friendEmotions.isEmpty()) {
                    friendDetailCardLastFriendEmotionCountTv.visibility = View.INVISIBLE
                    friendDetailCardLastFriendEmotionAiv.visibility = View.INVISIBLE
                } else {
                    friendDetailCardLastFriendEmotionCountTv.visibility = View.VISIBLE
                    friendDetailCardLastFriendEmotionAiv.visibility = View.VISIBLE

                    val count = getFriedRecord.emotionResponse.friendEmotions.size

                    if(count >= 10) {
                        friendDetailCardLastFriendEmotionCountTv.text = "9+"
                    } else {
                        friendDetailCardLastFriendEmotionCountTv.text = "+$count"
                    }
                }

                if(getFriedRecord.emotionResponse.myEmotion == null) {
                    context?.let{ context ->
                        Glide.with(context).load(R.drawable.emoji_mint_28).into(friendDetailCardFirstFriendEmotionAiv)
                    }
                }

                friendDetailCardFirstFriendEmotionAiv.setOnClickListener {
                    val params = friendEmojiRegisterCl.layoutParams
                    if(friendEmojiRegisterCl.visibility == View.VISIBLE) {
                        friendEmojiRegisterCl.visibility = View.GONE
                        params.height = 0
                    } else {
                        friendEmojiRegisterCl.visibility = View.VISIBLE
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    friendEmojiRegisterCl.layoutParams = params
                }
            }
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