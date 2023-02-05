package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// 화면 회전을 생각하면 만드는게 맞을듯.
@HiltViewModel
class AddGoalCalendarViewModel @Inject constructor() : BaseViewModel() {
    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> = _endDate

    fun setStartDate(date: String) { // 날짜 형태로 가공 필요
        _startDate.value = date
    }

    fun setEndDate(date: String) {
        _endDate.value = date
    }
}