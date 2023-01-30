package com.teampome.pome.network

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.UserInfoData
import com.teampome.pome.model.request.PhoneNumberDataBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST
    suspend fun refreshToken(
        @Body phoneNumberDataBody: PhoneNumberDataBody
    ) : Response<BasePomeResponse<UserInfoData>>
}