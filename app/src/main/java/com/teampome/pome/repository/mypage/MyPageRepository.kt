package com.teampome.pome.repository.mypage

import com.teampome.pome.model.request.PhoneNumberDataBody
import javax.inject.Inject

class MyPageRepository @Inject constructor(
    private val myPageDataSource: MyPageDataSource
) {
    fun getMarshmello() = myPageDataSource.getMarshmello()
}