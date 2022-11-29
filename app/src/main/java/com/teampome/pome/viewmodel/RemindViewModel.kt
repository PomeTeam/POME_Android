package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.RemindTestData
import com.teampome.pome.repository.remind.RemindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindViewModel @Inject constructor(
    private val repository: RemindRepository
) : ViewModel() {

    private val _testRemindList = MutableLiveData<RemindTestData?>()
    val testRemindList: LiveData<RemindTestData?> = _testRemindList

    init {
        // test data 주입
        viewModelScope.launch {
//            _testRemindList.value = null
            _testRemindList.value = repository.getTestRemindData()
        }
    }
}