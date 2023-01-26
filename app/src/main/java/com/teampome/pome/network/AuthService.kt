package com.teampome.pome.network

import com.teampome.pome.model.BasePomeResponse
import retrofit2.Response

interface AuthService {

    suspend fun refreshToken() : Response<BasePomeResponse<String>>
}