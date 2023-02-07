package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.AllGoalData
import com.teampome.pome.repository.goal.GoalRepository
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.Emotion
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConsumeEmotionViewModel @Inject constructor(
    private val repository: RecordRepository,
    private val goalRepository: GoalRepository
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

    private val _getGoalIdByGoalCategoryIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<AllGoalData>>>()
    val getGoalIdByGoalCategoryIdResponse: LiveData<ApiResponse<BasePomeResponse<AllGoalData>>> = _getGoalIdByGoalCategoryIdResponse

    fun getGoalIdByGoalCategoryId(
        goalCategoryId: Int,
        coroutineErrorHandler: CoroutineErrorHandler
    ) = baseRequest(
        _getGoalIdByGoalCategoryIdResponse,
        coroutineErrorHandler
    ) {
        goalRepository.getGoalIdByGoalCategoryId(goalCategoryId)
    }

    private val _goalId = MutableLiveData<Int>()
    val goalId: LiveData<Int> = _goalId

    fun setGoalId(goalId: Int?) {
        goalId?.let {
            _goalId.value = it
        }
    }
}