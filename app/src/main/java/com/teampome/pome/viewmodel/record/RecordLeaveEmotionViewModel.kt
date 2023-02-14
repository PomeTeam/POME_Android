package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordLeaveEmotionViewModel @Inject constructor(
    private val repository: RecordRepository
) : BaseViewModel() {
    private val _getOneWeekRecordByGoalIdResponse = MutableLiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>>()
    val getOneWeekRecordByGoalIdResponse: LiveData<ApiResponse<BasePomeResponse<BaseAllData<RecordData>>>> = _getOneWeekRecordByGoalIdResponse

    val oneWeekRecords : LiveData<List<RecordData>> = Transformations.map(_getOneWeekRecordByGoalIdResponse) {
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

    fun getOneWeekRecordByGoalId(goalId: Int, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getOneWeekRecordByGoalIdResponse,
        coroutineErrorHandler
    ) {
        repository.getOneWeekGoalByGoalId(goalId)
    }
}