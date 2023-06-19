package com.teampome.pome.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

fun Reason.getString(): String {
    return when(this) {
        is Reason.BotheringRecord -> {
            "기록이 귀찮아요"
        }
        is Reason.ManyAlarm -> {
            "알림이 너무 많아요"
        }
        is Reason.BlockAccount -> {
            "억울하게 이용이 제한됐어요"
        }
        is Reason.MakeNewOne -> {
            "새 계정을 만들고 싶어요"
        }
        is Reason.Empty -> {
            ""
        }
    }
}