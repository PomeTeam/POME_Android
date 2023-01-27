package com.teampome.pome.network

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.PhoneDataBody
import com.teampome.pome.model.SmsData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    /**
     *  회원가입 시, 인증번호를 요청한다.
     */
    @POST("api/v1/sms/send")
    suspend fun sendSms(
        @Body phoneBody: PhoneDataBody
    ) : Response<BasePomeResponse<SmsData>>
}