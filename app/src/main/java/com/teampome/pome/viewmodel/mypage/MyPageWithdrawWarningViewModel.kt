package com.teampome.pome.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.repository.mypage.MyPageRepository
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseViewModel
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.util.token.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MyPageWithdrawWarningViewModel @Inject constructor(
    private val userManager: UserManager,
    private val repository: MyPageRepository
) : BaseViewModel() {

    val userName = runBlocking {
        userManager.getUserNickName().first()
    }

    fun removeAllUserData() {
        viewModelScope.launch {
            userManager.deleteAllUserData()
        }
    }

    private val _deleteUserResponse = MutableLiveData<ApiResponse<BasePomeResponse<Boolean>>>()
    val deleteUserResponse: LiveData<ApiResponse<BasePomeResponse<Boolean>>> = _deleteUserResponse

    fun deleteUser(reason: String, coroutineErrorHandler: CoroutineErrorHandler) = baseRequest(_deleteUserResponse, coroutineErrorHandler) {
        repository.deleteUser(reason)
    }
}