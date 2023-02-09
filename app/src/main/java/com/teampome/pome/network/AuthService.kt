package com.teampome.pome.network

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.AuthDataBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/auth/renew")
    suspend fun refreshToken(
        @Body authDataBody: AuthDataBody
    ) : Response<BasePomeResponse<UserInfoData>>
}