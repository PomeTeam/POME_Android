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
) : ListAdapter<GetFriendRecord, FriendRecordGetAdapter.FriendGetRecordViewHolder>(BookDiffCallback) {

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
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FriendGetRecordViewHolder, position: Int) {
        val friends = currentList[position]
        holder.bind(friends)
    }

    inner class FriendGetRecordViewHolder(
        private val friendDetailRecordClickListener: FriendDetailRecordClickListener,
        private val binding : ItemFriendDetailCardBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(getFriedRecord: GetFriendRecord){
            with(binding) {
                getFriendRecord = getFriedRecord

                friendDetailMoreSettingIv.setOnClickListener {
                    friendDetailRecordClickListener.onFriendDetailMoreClick(getFriedRecord.id)
                }

                val timeString = getTimeAgo(getFriedRecord.createdAt)
                friendDetailContentNameTimeTv.text = " · ${getFriedRecord.oneLineMind} · $timeString"

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
                    itemView.context?.let{ context ->
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

                val friends = friendsMap[getFriedRecord.nickname]

                friends?.let { friend ->
                    Glide.with(itemView.context)
                        .load(friend.imageKey)
                        .circleCrop()
                        .into(friendDetailProfileIv)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun getTimeAgo(isoTime: String): String {
            // 마이크로 초 부분을 잘라냅니다.
            val modifiedIsoTime = isoTime.substring(0, isoTime.lastIndexOf(".") + 4) + "Z"
            val time = Instant.parse(modifiedIsoTime)
            val now = Instant.now()
            val duration = Duration.between(time, now)

            return when {
                duration.toMinutes() < 1 -> "방금 전"
                duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
                duration.toHours() < 24 -> "${duration.toHours()}시간 전"
                duration.toDays() < 2 -> "어제"
                else -> ZonedDateTime.parse(modifiedIsoTime).format(DateTimeFormatter.ofPattern("M월 d일"))
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