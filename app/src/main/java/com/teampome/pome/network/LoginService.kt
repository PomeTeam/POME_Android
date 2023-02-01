package com.teampome.pome.network

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.PhoneNumberDataBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("api/v1/users/sign-in")
    suspend fun login(
        @Body phoneNumber: PhoneNumberDataBody
    ) : Response<BasePomeResponse<UserInfoData>>
}