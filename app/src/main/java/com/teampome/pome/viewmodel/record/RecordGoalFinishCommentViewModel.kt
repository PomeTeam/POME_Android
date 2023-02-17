package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.repository.goal.GoalRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordGoalFinishCommentViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : BaseViewModel() {

    private val _deleteGoalResponse = MutableLiveData<ApiResponse<BasePomeResponse<Any>>>()
    val deleteGoalResponse: LiveData<ApiResponse<BasePomeResponse<Any>>> = _deleteGoalResponse

    fun deleteGoal(goalId: Int, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _deleteGoalResponse,
        coroutineErrorHandler
    ) {
        goalRepository.deleteGoal(goalId)
    }

    private val _finishGoalResponse = MutableLiveData<ApiResponse<BasePomeResponse<GoalData>>>()
    val finishGoalResponse: LiveData<ApiResponse<BasePomeResponse<GoalData>>> = _finishGoalResponse

    fun finishGoal(goalId: Int, oneLineComment: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _finishGoalResponse,
        coroutineErrorHandler
    ) {
        goalRepository.finishGoal(goalId, oneLineComment)
    }

    private val _oneLineComment = MutableLiveData<String>()
    val oneLineComment: LiveData<String> = _oneLineComment

    fun setOneLineComment(comment: String) {
        _oneLineComment.value = comment
    }
}