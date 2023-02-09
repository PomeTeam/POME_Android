package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.TestAlarmsData
import com.teampome.pome.repository.record.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordAlarmsViewModel @Inject constructor(
    private val repository: RecordRepository
) : ViewModel() {
    private val _testAlarmsData = MutableLiveData<List<TestAlarmsData>>()
    val testAlarmsData: LiveData<List<TestAlarmsData>> = _testAlarmsData

    init {
        viewModelScope.launch {
            _testAlarmsData.value = repository.getRecordAlarmsTestData()
        }
    }
}