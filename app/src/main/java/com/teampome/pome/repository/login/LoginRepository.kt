package com.teampome.pome.repository.login

import com.teampome.pome.model.request.PhoneNumberDataBody
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginDataSource: LoginDataSource
) {
    fun login(phoneNumber: String) = loginDataSource.login(PhoneNumberDataBody(phoneNumber))
}