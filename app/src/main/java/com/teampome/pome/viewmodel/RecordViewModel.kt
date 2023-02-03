package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.RecordTestData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.AllGoalData
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

    private val _recordTestData = MutableLiveData<RecordTestData?>()
    val recordTestData : LiveData<RecordTestData?> = _recordTestData

    init {
        viewModelScope.launch {
            _recordTestData.value = recordRepository.getRecordTestData()
        }
    }

    private val _recordDataByUserIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<List<RecordData>>>>()
    val recordDataByUserIdResponse: LiveData<ApiResponse<BasePomeResponse<List<RecordData>>>> = _recordDataByUserIdResponse

    private val _findAllGoalByUserResponse = MutableLiveData<ApiResponse<BasePomeResponse<AllGoalData>>>()
    val findAllGoalByUserResponse: LiveData<ApiResponse<BasePomeResponse<AllGoalData>>> = _findAllGoalByUserResponse

    private val _getGoalByGoalIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<GoalData>>>()
    val getGoalByGoalIdResponse: LiveData<ApiResponse<BasePomeResponse<GoalData>>> = _getGoalByGoalIdResponse

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
}