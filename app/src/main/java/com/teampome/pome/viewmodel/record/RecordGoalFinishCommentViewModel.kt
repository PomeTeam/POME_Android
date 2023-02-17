package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.base.BasePomeResponse
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
}