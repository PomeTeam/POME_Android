package com.teampome.pome.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageWithdrawViewModel @Inject constructor() : BaseViewModel() {

    private val _reason1 = MutableLiveData(false)
    val reason1: LiveData<Boolean> = _reason1

    private val _reason2 = MutableLiveData(false)
    val reason2: LiveData<Boolean> = _reason2

    private val _reason3 = MutableLiveData(false)
    val reason3: LiveData<Boolean> = _reason3

    private val _reason4 = MutableLiveData(false)
    val reason4: LiveData<Boolean> = _reason4

    fun reason1Click() {
        _reason1.value = _reason1.value != true

        _reason2.value = false
        _reason3.value = false
        _reason4.value = false
    }

    fun reason2Click() {
        _reason2.value = _reason2.value != true

        _reason1.value = false
        _reason3.value = false
        _reason4.value = false
    }

    fun reason3Click() {
        _reason3.value = _reason3.value != true

        _reason1.value = false
        _reason2.value = false
        _reason4.value = false
    }

    fun reason4Click() {
        _reason4.value = _reason4.value != true

        _reason1.value = false
        _reason2.value = false
        _reason3.value = false
    }
}