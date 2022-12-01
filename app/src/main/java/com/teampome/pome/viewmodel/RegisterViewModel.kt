package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {

    // Todo : Two-way Binding 전에 일단 데이터를 액티비티에서 주입할 수 있게함
    val _registerName = MutableLiveData<String>()
    val registerName : LiveData<String> = _registerName

    val _registerPhone = MutableLiveData<String>()
    val registerPhone : LiveData<String> = _registerPhone

    val _registerCertNum = MutableLiveData<String>()
    val registerCertNum : LiveData<String> = _registerCertNum
}