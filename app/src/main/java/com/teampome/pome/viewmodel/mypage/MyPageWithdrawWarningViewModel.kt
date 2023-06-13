package com.teampome.pome.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.token.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MyPageWithdrawWarningViewModel @Inject constructor(
    private val userManager: UserManager
) : BaseViewModel() {

    val userName = runBlocking {
        userManager.getUserNickName().first()
    }

    fun removeAllUserData() {
        viewModelScope.launch {
            userManager.deleteAllUserData()
        }
    }
}