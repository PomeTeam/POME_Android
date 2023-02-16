package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.util.SingleLiveEvent
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordGoalFinishViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : BaseViewModel() {
    private val _curGoalData = MutableLiveData<GoalData>()
    val curGoalData: LiveData<GoalData> = _curGoalData

    fun setCurGoalData(goalData: GoalData) {
        _curGoalData.value = goalData
    }

    private val _getRecordByGoalIdResponse = SingleLiveEvent<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>()
    val getRecordByGoalIdResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = _getRecordByGoalIdResponse

    fun getGoalRecords(
        goalId: Int,
        coroutineErrorHandler: CoroutineErrorHandler
    ) = baseRequest(
        _getRecordByGoalIdResponse,
        coroutineErrorHandler
    ) {
        recordRepository.getRecordByGoalId(goalId)
    }
}