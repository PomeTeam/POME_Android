package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConsumeRecordViewModel @Inject constructor(): BaseViewModel(){
    private val _consumePrice = MutableLiveData<Int>()
    val consumePrice: LiveData<Int> = _consumePrice

    private val _consumeRecord = MutableLiveData<String>()
    val consumeRecord: LiveData<String> = _consumeRecord

    fun setPrice(price: Int) {
        _consumePrice.value = price
    }

    fun setRecord(record: String) {
        _consumeRecord.value = record
    }
}