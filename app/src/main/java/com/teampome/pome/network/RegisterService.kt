package com.teampome.pome.network

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.request.PhoneDataBody
import com.teampome.pome.model.SmsData
import com.teampome.pome.model.UserInfoData
import com.teampome.pome.model.request.NicknameDataBody
import com.teampome.pome.model.request.UserInfoDataBody
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

    /**
     *  회원가입 시, 닉네임 중복여부를 확인한다.
     */
    @POST("api/v1/users/check-nickname")
    suspend fun checkNickname(
        @Body nickName: NicknameDataBody
    ) : Response<BasePomeResponse<Boolean>>

    /**
     *  회원가입
     */
    @POST("api/v1/users/sign-up")
    suspend fun signUp(
        @Body userInfo: UserInfoDataBody
    ) : Response<BasePomeResponse<UserInfoData>>
}