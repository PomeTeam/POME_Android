package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordGoalFinishViewModel @Inject constructor() : BaseViewModel() {
    private val _curGoalData = MutableLiveData<GoalData>()
    val curGoalData: LiveData<GoalData> = _curGoalData

    fun setCurGoalData(goalData: GoalData) {
        _curGoalData.value = goalData
    }
}