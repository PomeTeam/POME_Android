package com.teampome.pome.network

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.PhoneDataBody
import com.teampome.pome.model.SmsData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("api/v1/sms/send")
    suspend fun sendSms(
        @Body phoneBody: PhoneDataBody
    ) : Response<BasePomeResponse<SmsData>>
}