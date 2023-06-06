package com.teampome.pome.viewmodel.mypage

import android.util.Log
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.token.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MyPageSettingViewModel @Inject constructor(
    private val userManager: UserManager
) : BaseViewModel() {
    val userName = runBlocking {
        userManager.getUserNickName().first()
    }

    val userProfileUrl = runBlocking {
        userManager.getUserProfileUrl().first()
    }

    init {
        Log.d("test", "test username : $userName , $userProfileUrl")
    }
}