package com.teampome.pome.repository.register

import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val registerDataSource: RegisterRemoteDataSource
) {
    fun sendSms(phoneNum: String) = registerDataSource.sendSms(phoneNum)

    fun checkNickname(nickName: String) = registerDataSource.checkNickname(nickName)
}