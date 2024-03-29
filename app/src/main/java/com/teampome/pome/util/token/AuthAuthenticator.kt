package com.teampome.pome.util.token

import android.util.Log
import com.teampome.pome.BuildConfig.BASE_URL
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.AuthDataBody
import com.teampome.pome.network.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val userManager: UserManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("authenticate", "authenticate call by $response")

        val userId = runBlocking {
            userManager.getUserId().first()
        }

        val userNickname = runBlocking {
            userManager.getUserNickName().first()
        }

        return runBlocking {
            if(userId == null || userNickname == null) {
                return@runBlocking null
            }

            userManager.deleteUserId()
            userManager.deleteUserNickName()

            val newToken = getNewToken(userId, userNickname)
            if(!newToken.isSuccessful || newToken.body() == null) {
                tokenManager.deleteToken()
            }

            newToken.body()?.let { basePomeResponse ->
                basePomeResponse.data?.let {
                    userManager.saveUserId(it.userId)
                    userManager.saveUserNickName(it.nickName)
                    tokenManager.saveToken(it.accessToken)
                    response.request.newBuilder()
                        .header("ACCESS-TOKEN", it.accessToken)
                        .build()
                }
            }
        }
    }

    private suspend fun getNewToken(userId: String, userNickname: String): retrofit2.Response<BasePomeResponse<UserInfoData>> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(AuthService::class.java)
        return service.refreshToken(AuthDataBody(userId, userNickname))
    }
}