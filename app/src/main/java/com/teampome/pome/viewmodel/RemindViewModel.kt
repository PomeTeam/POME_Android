package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalCategory
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.repository.goal.GoalRepository
import com.teampome.pome.repository.remind.RemindRepository
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.common.SingleLiveEvent
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemindViewModel @Inject constructor(
    private val remindRepository: RemindRepository,
    private val goalRepository: GoalRepository
) : BaseViewModel() {
    private val _getRemindRecordsResponse = SingleLiveEvent<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>()
    val getRemindRecordsResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = _getRemindRecordsResponse

    fun getRemindRecords(goalId: Int, firstEmotion: Int?, secondEmotion: Int?, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getRemindRecordsResponse,
        coroutineErrorHandler
    ) {
        remindRepository.getRemindRecords(goalId, firstEmotion, secondEmotion)
    }

    private val _findEndGoalsResponse = SingleLiveEvent<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>>()
    val findEndGoalsResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = _findEndGoalsResponse

    val goalCategory: LiveData<List<GoalCategory?>> = Transformations.map(_findEndGoalsResponse) {
        when(it) {
            is ApiResponse.Success -> {
                it.data.data?.let { allGoalData ->
                    allGoalData.content.map { data ->
                        data?.let { gd ->
                            GoalCategory(
                                gd.id,
                                gd.name,
                                false,
                                gd.id,
                                CommonUtil.calDiffDate(gd.endDate) == 0
                            )
                        } ?: run { null }
                    }
                } ?: run { null }
            }
            is ApiResponse.Loading -> { null }
            is ApiResponse.Failure -> { null }
        }
    }

    val goalDetails: LiveData<List<GoalData?>> = Transformations.map(_findEndGoalsResponse) {
        when(it) {
            is ApiResponse.Success -> {
                it.data.data?.content ?: run { null }
            }
            is ApiResponse.Loading -> { null }
            is ApiResponse.Failure -> { null }
        }
    }

    fun findEndGoals(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _findEndGoalsResponse,
        coroutineErrorHandler
    ) {
        goalRepository.findEndGoals()
    }
}