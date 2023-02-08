package com.teampome.pome.viewmodel.record

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.RecordTestData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalCategory
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.repository.goal.GoalRepository
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val goalRepository: GoalRepository
) : BaseViewModel() {
    private val _recordDataByUserIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<List<RecordData>>>>()
    val recordDataByUserIdResponse: LiveData<ApiResponse<BasePomeResponse<List<RecordData>>>> = _recordDataByUserIdResponse

    private val _findAllGoalByUserResponse = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>>()
    val findAllGoalByUserResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = _findAllGoalByUserResponse

    private val _getGoalByGoalIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<GoalData>>>()
    val getGoalByGoalIdResponse: LiveData<ApiResponse<BasePomeResponse<GoalData>>> = _getGoalByGoalIdResponse

    private val _deleteGoalResponse = MutableLiveData<ApiResponse<BasePomeResponse<Any>>>()
    val deleteGoalResponse: LiveData<ApiResponse<BasePomeResponse<Any>>> = _deleteGoalResponse

    val goalCategory: LiveData<List<GoalCategory>> = Transformations.map(_findAllGoalByUserResponse) {
        when(it) {
            is ApiResponse.Success -> {
                it.data.data?.let { allGoalData ->
                    allGoalData.content?.let { goalData ->
                        goalData.map { data ->
                            GoalCategory(
                                data.goalCategoryResponse.id,
                                data.goalCategoryResponse.name,
                                false,
                                data.id
                            )
                        }
                    } ?: run { null }
                } ?: run { null }
            }
            is ApiResponse.Loading -> { null }
            is ApiResponse.Failure -> { null }
        }
    }

    val goalDetails: LiveData<List<GoalData>> = Transformations.map(_findAllGoalByUserResponse) {
        when(it) {
            is ApiResponse.Success -> {
                it.data.data?.let { allGoalData ->
                    allGoalData.content ?: run { null }
                } ?: run { null }
            }
            is ApiResponse.Loading -> { null }
            is ApiResponse.Failure -> { null }
        }
    }

    fun recordDataByUserId(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _recordDataByUserIdResponse,
        coroutineErrorHandler
    ) {
        recordRepository.getRecordDataByUserId()
    }

    fun findAllGoalByUser(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _findAllGoalByUserResponse,
        coroutineErrorHandler
    ) {
        goalRepository.findAllGoalByUser()
    }

    fun getGoalByGoalId(goalId: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getGoalByGoalIdResponse,
        coroutineErrorHandler
    )  {
        goalRepository.getGoalByGoalId(goalId)
    }

    fun deleteGoal(goalId: Int, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _deleteGoalResponse,
        coroutineErrorHandler
    ) {
        goalRepository.deleteGoal(goalId)
    }

    private val _getRecordByGoalIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>()
    val getRecordByGoalIdResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = _getRecordByGoalIdResponse

    fun getRecordByGoalId(goalId: Int, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getRecordByGoalIdResponse,
        coroutineErrorHandler
    ) {
        recordRepository.getRecordByGoalId(goalId)
    }

    private val _getOneWeekRecordByGoalIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>()
    val getOneWeekRecordByGoalIdResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = _getOneWeekRecordByGoalIdResponse

    fun getOneWeekRecordByGoalId(goalId: Int, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getOneWeekRecordByGoalIdResponse,
        coroutineErrorHandler
    ) {
        recordRepository.getOneWeekGoalByGoalId(goalId)
    }
}