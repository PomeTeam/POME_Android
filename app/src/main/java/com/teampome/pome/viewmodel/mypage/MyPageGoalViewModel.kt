package com.teampome.pome.viewmodel.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.repository.goal.GoalRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository
): BaseViewModel() {

    private val _getEndGoalResponse = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>>()
    val getEndGoalResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = _getEndGoalResponse

    fun findEndGoals(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getEndGoalResponse,
        coroutineErrorHandler
    ) {
        goalRepository.findEndGoals()
    }

    val endGoalCount = Transformations.map(_getEndGoalResponse) {
        when(it) {
            is ApiResponse.Loading -> { 0 }
            is ApiResponse.Failure -> { 0 }
            is ApiResponse.Success -> {
                it.data.data?.content?.size ?: 0
            }
        }
    }

    private val _deleteEndGoalResponse = MutableLiveData<ApiResponse<BasePomeResponse<Any>>>()
    val deleteEndGoalResponse: LiveData<ApiResponse<BasePomeResponse<Any>>> = _deleteEndGoalResponse

    fun deleteEndGoal(goalId: Int, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _deleteEndGoalResponse,
        coroutineErrorHandler
    ) {
        goalRepository.deleteGoal(goalId)
    }
}