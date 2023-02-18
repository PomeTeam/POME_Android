package com.teampome.pome.repository.mypage

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.mytab.MyTabMarshmello
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.PhoneNumberDataBody
import com.teampome.pome.network.LoginService
import com.teampome.pome.network.MyTabService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPageRemoteDataSource @Inject constructor(
    private val service: MyTabService
): MyPageDataSource{

    override fun getMarshmello(): Flow<ApiResponse<BasePomeResponse<MyTabMarshmello>>> = apiRequestFlow {
        service.getMarshmello()
    }

}