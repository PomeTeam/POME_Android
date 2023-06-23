package com.teampome.pome.presentation.friend

import com.teampome.pome.model.response.FriendEmotionResponse

//친구 기록 클릭 리스너
interface FriendDetailRecordClickListener {

    fun onFriendDetailMoreClick(recordId : Int)

    fun onFriendDetailEmojiClick(emojiList : List<FriendEmotionResponse>)

}