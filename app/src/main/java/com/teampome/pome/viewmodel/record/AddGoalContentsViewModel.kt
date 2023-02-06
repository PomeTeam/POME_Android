package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.repository.goal.GoalRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddGoalContentsViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : BaseViewModel() {
    private val _goalCategory = MutableLiveData<String>()
    val goalCategory: LiveData<String> = _goalCategory

    private val _oneLineMind = MutableLiveData<String>()
    val oneLineMind: LiveData<String> = _oneLineMind

    private val _goalPrice = MutableLiveData<String>()
    val goalPrice: LiveData<String> = _goalPrice

    private val _private = MutableLiveData<Boolean>(true)
    val private: LiveData<Boolean> = _private

    fun setCategory(category: String) {
        _goalCategory.value = category
    }

    fun setOneLineMind(mind: String) {
        _oneLineMind.value = mind
    }

    fun setGoalPrice(price: String) {
        _goalPrice.value = price
    }

    fun setPrivate(private: Boolean) {
        _private.value = private
    }

    private val _makeGoalResponse = MutableLiveData<ApiResponse<BasePomeResponse<GoalData>>>()
    val makeGoalResponse: LiveData<ApiResponse<BasePomeResponse<GoalData>>> = _makeGoalResponse

    fun makeGoal(
        endDate: String,
        startDate: String,
        coroutineErrorHandler: CoroutineErrorHandler
    ) = baseRequest(
        _makeGoalResponse,
        coroutineErrorHandler
    ) {
        goalRepository.makeGoal(endDate, private.value ?: false, goalCategory.value ?: "", oneLineMind.value ?: "", goalPrice.value?.toLong() ?: 0, startDate)
    }
}