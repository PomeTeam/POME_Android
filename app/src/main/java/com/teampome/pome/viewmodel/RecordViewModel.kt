package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.RecordTestData
import com.teampome.pome.repository.record.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val repository: RecordRepository
) : ViewModel() {

    private val _recordTestData = MutableLiveData<RecordTestData?>()
    val recordTestData : LiveData<RecordTestData?> = _recordTestData

    init {
        viewModelScope.launch {
            _recordTestData.value = repository.getRecordTestData()
        }
    }
}