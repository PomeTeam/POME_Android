package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterTermsViewModel @Inject constructor() : ViewModel() {

    val _agreeAllCheck = MutableLiveData<Boolean>()
    val agreeAllCheck: LiveData<Boolean> = _agreeAllCheck

    val _agreeUsingTermsCheck = MutableLiveData<Boolean>()
    val agreeUsingTermsCheck: LiveData<Boolean> = _agreeUsingTermsCheck

    val _agreePrivacyCheck = MutableLiveData<Boolean>()
    val agreePrivacyCheck: LiveData<Boolean> = _agreePrivacyCheck

    val _agreeMarketingCheck = MutableLiveData<Boolean>()
    val agreeMarketingCheck: LiveData<Boolean> = _agreeMarketingCheck

    init {
        _agreeAllCheck.value = false
        _agreeUsingTermsCheck.value = false
        _agreePrivacyCheck.value = false
        _agreeMarketingCheck.value = false
    }
}