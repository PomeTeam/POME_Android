package com.teampome.pome.network

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.UserInfoData
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