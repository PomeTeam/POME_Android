package com.teampome.pome.util.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

open class BaseViewModel : ViewModel() {
    private var job: Job? = null

    // Coroutine Flow를 연결한 BaseRequest
    // CoroutineExceptionHandler => 코루틴 내에서 Error 발생시 호출
    protected fun <T> baseRequest(liveData: MutableLiveData<T>, errorHandler: CoroutineErrorHandler, request: () -> Flow<T>) {
        job = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler {_, error ->
            viewModelScope.launch(Dispatchers.Main) {
                errorHandler.onError(error.localizedMessage ?: "API Request 도중 Error 발생")
            }
        }) {
            request().collect {
                withContext(Dispatchers.Main) {
                    liveData.value = it
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.let {
            if(it.isActive) {
                it.cancel()
            }
        }
    }
}

interface CoroutineErrorHandler {
    fun onError(message: String)
}