package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.mytab.MyTabMarshmello
import com.teampome.pome.repository.mypage.MyPageRepository
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.util.token.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyTabViewModel @Inject constructor(
    private val repository: MyPageRepository
): BaseViewModel() {

    private val _getMarshmello = MutableLiveData<ApiResponse<BasePomeResponse<List<MyTabMarshmello>>>>()
    val getMarshmello: LiveData<ApiResponse<BasePomeResponse<List<MyTabMarshmello>>>> = _getMarshmello

    fun getMarshmello(coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(
        _getMarshmello,
        coroutineErrorHandler
    ){
        repository.getMarshmello()
    }
}