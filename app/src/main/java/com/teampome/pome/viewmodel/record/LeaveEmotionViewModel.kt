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
class LeaveEmotionViewModel @Inject constructor(
    private val repository: RecordRepository
) : BaseViewModel() {
    private val _selectedEmotion = MutableLiveData<Emotion>()
    val selectedEmotion: LiveData<Emotion> = _selectedEmotion

    fun setEmotion(emotion: Emotion) {
        _selectedEmotion.value = emotion
    }

    private val _writeSecondEmotionResponse = MutableLiveData<ApiResponse<BasePomeResponse<RecordData>>>()
    val writeSecondEmotionResponse: LiveData<ApiResponse<BasePomeResponse<RecordData>>> = _writeSecondEmotionResponse

    fun writeSecondEmotion(recordId: Int, emotionId: Int, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _writeSecondEmotionResponse,
        coroutineErrorHandler
    ) {
        repository.writeSecondEmotion(
            recordId,
            emotionId
        )
    }
}