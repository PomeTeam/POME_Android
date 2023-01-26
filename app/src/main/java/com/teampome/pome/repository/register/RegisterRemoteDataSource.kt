package com.teampome.pome.repository.register

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.PhoneDataBody
import com.teampome.pome.model.SmsData
import com.teampome.pome.network.RegisterService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRemoteDataSource @Inject constructor(
    private val service: RegisterService
) : RegisterDataSource {
    override fun sendSms(phoneNum: String): Flow<ApiResponse<BasePomeResponse<SmsData>>> = apiRequestFlow {
        service.sendSms(PhoneDataBody(phoneNum))
    }
}