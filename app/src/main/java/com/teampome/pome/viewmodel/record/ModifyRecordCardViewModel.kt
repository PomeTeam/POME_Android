package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ModifyRecordCardViewModel @Inject constructor(
    private val repository: RecordRepository
) : BaseViewModel() {
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    fun setDate(date: String) {
        _date.value = date
    }

    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price

    fun setPrice(price: String) {
        _price.value = price
    }

    private val _useComment = MutableLiveData<String>()
    val useComment: LiveData<String> = _useComment

    fun setUseComment(comment: String) {
        _useComment.value = comment
    }

    private val _updateRecordResponse = MutableLiveData<ApiResponse<BasePomeResponse<RecordData>>>()
    val updateRecordResponse: LiveData<ApiResponse<BasePomeResponse<RecordData>>> = _updateRecordResponse

    fun updateRecord(
        recordId: Int,
        goalId: Int,
        useComment: String,
        useDate: String,
        usePrice: Long,
        coroutineErrorHandler: CoroutineErrorHandler
    ) = baseRequest(
        _updateRecordResponse,
        coroutineErrorHandler
    ) {
        repository.updateRecord(recordId, goalId, useComment, useDate, usePrice)
    }
}