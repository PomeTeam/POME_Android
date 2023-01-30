package com.teampome.pome.util.token

import com.teampome.pome.di.BASE_URL
import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.UserInfoData
import com.teampome.pome.model.request.PhoneNumberDataBody
import com.teampome.pome.network.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val userManager: UserManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getToken().first()
        }

        val phoneNum = runBlocking {
            userManager.getUserPhone().first()
        }

        return runBlocking {
            if(phoneNum == null) {
                return@runBlocking null
            }
            val newToken = getNewToken(phoneNum)

            if(!newToken.isSuccessful || newToken.body() == null) {
                tokenManager.deleteToken()
            }

            newToken.body()?.let { basePomeResponse ->
                basePomeResponse.data?.let {
                    tokenManager.saveToken(it.accessToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $it")
                        .build()
                }
            }
        }
    }

    private suspend fun getNewToken(phoneNum: String): retrofit2.Response<BasePomeResponse<UserInfoData>> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(AuthService::class.java)
        return service.refreshToken(PhoneNumberDataBody(phoneNum))
    }
}