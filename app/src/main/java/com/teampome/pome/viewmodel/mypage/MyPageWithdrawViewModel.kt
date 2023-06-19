package com.teampome.pome.viewmodel.mypage

import android.content.Context
import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageWithdrawViewModel @Inject constructor() : BaseViewModel() {

    private val _reason = MutableLiveData<Reason>(Reason.Empty)
    val reason: LiveData<Reason> = _reason

    fun reasonClick(reason: Reason) {
        if(_reason.value == reason) {
            _reason.value = Reason.Empty
        } else {
            _reason.value = reason
        }
    }
}

// dataBinding에서 sealed class 접근하지 못함! -> BindingAdapter 이용
sealed class Reason {
    object BotheringRecord: Reason()
    object ManyAlarm: Reason()
    object BlockAccount: Reason()
    object MakeNewOne: Reason()
    object Empty: Reason()
}

fun Reason.getString(context: Context): String {
    return when(this) {
        is Reason.BotheringRecord -> {
            context.resources.getString(R.string.mypage_withdraw_reason_1_text)
        }
        is Reason.ManyAlarm -> {
            context.resources.getString(R.string.mypage_withdraw_reason_2_text)
        }
        is Reason.BlockAccount -> {
            context.resources.getString(R.string.mypage_withdraw_reason_3_text)
        }
        is Reason.MakeNewOne -> {
            context.resources.getString(R.string.mypage_withdraw_reason_4_text)
        }
        is Reason.Empty -> {
            context.resources.getString(R.string.empty_string)
        }
    }
}