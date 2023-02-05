package com.teampome.pome.viewmodel.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddGoalContentsViewModel @Inject constructor() : BaseViewModel() {
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
}