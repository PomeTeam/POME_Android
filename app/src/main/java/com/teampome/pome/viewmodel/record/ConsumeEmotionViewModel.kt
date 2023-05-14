package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.util.common.Emotion
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConsumeEmotionViewModel @Inject constructor(
    private val repository: RecordRepository
) : BaseViewModel() {
    private val _selectedEmotion = MutableLiveData<Emotion>()
    val selectedEmotion: LiveData<Emotion> = _selectedEmotion

    fun setEmotion(emotion: Emotion) {
        _selectedEmotion.value = emotion
    }

    private val _writeConsumeRecordResponse = MutableLiveData<ApiResponse<BasePomeResponse<RecordData>>>()
    val writeConsumeRecordResponse: LiveData<ApiResponse<BasePomeResponse<RecordData>>> = _writeConsumeRecordResponse

    fun writeConsumeRecord(
        emotionId: Int,
        goalId: Int,
        useComment: String,
        useDate: String,
        usePrice: Long,
        coroutineErrorHandler: CoroutineErrorHandler
    ) = baseRequest(
        _writeConsumeRecordResponse,
        coroutineErrorHandler
    ) {
        repository.writeConsumeRecord(emotionId, goalId, useComment, useDate, usePrice)
    }
}