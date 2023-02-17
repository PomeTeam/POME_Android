package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.repository.remind.RemindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindViewModel @Inject constructor(
    private val repository: RemindRepository
) : ViewModel() {

    private val _remindPosition = MutableLiveData<Int>()
    val remindPosition: LiveData<Int> = _remindPosition

//    private val _firstEmotion = MutableLiveData(Emotion.FIRST_EMOTION)
//    val firstEmotion: LiveData<Emotion> = _firstEmotion
//
//    private val _lastEmotion = MutableLiveData(Emotion.LAST_EMOTION)
//    val lastEmotion: LiveData<Emotion> = _lastEmotion

    init {
        // 초기값
        _remindPosition.value = 0
    }

    fun settingRemindPosition(pos: Int) {
        _remindPosition.value = pos
    }

//    fun settingFirstEmotion(emotion: Emotion) {
//        _firstEmotion.value = emotion
//    }
//
//    fun settingLastEmotion(emotion: Emotion) {
//        _lastEmotion.value = emotion
//    }
}