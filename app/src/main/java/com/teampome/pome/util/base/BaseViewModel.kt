package com.teampome.pome.util.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.util.common.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

open class BaseViewModel : ViewModel() {
    private var job: Job? = null

    /**
     *   Coroutine Flow를 연결한 BaseRequest
     *   CoroutineExceptionHandler => 코루틴 내에서 Error 발생시 호출된다.
     *
     *   정상적으로 data를 가져오는 경우, liveData에 값을 쓴다.
     *   error가 발생한 경우, errorHandler에 error결과를 콜백한다.
     *   뷰모델이 destroy되는 경우, 자동으로 job을 종료시킨다.
     *
     *   병렬 실행이 불가능해보이는데?... 개선 필요
     */
    protected fun <T> baseRequest(liveData: MutableLiveData<T>, errorHandler: CoroutineErrorHandler, request: () -> Flow<T>) {
        job = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler {_, error ->
            viewModelScope.launch(Dispatchers.Main) {
                error.printStackTrace()
                errorHandler.onError(error.message ?: "API Request 도중 Error 발생 $error")
            }
        }) {
            request().collect {
                withContext(Dispatchers.Main) {
                    liveData.value = it
                }
            }
        }
    }

    protected fun <T> baseEventRequest(liveData: MutableLiveData<Event<T>>, errorHandler: CoroutineErrorHandler, request: () -> Flow<T>) {
        job = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler {_, error ->
            viewModelScope.launch(Dispatchers.Main) {
                error.printStackTrace()
                errorHandler.onError(error.message ?: "API Request 도중 Error 발생 $error")
            }
        }) {
            request().collect {
                withContext(Dispatchers.Main) {
                    liveData.value = Event(it)
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