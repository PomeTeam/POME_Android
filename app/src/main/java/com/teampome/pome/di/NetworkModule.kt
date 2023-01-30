package com.teampome.pome.di

import android.content.Context
import com.teampome.pome.network.AuthService
import com.teampome.pome.network.ImageService
import com.teampome.pome.network.PreSignedImageService
import com.teampome.pome.network.RegisterService
import com.teampome.pome.util.token.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

const val BASE_URL = "http://52.79.89.129/"
const val IMAGE_BASE_URL = "http://image-main-server.ap-northeast-2.elasticbeanstalk.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideUserManager(@ApplicationContext context: Context): UserManager = UserManager(context)

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor = AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator = AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideUserUrlInterceptor(userManager: UserManager): ImageUrlInterceptor = ImageUrlInterceptor(userManager)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logginInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder() : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit.Builder) : AuthService =
        retrofit
            .build()
            .create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideRegisterService(retrofit: Retrofit.Builder) : RegisterService =
        retrofit
            .build()
            .create(RegisterService::class.java)

    @Singleton
    @Provides
    fun provideImageService() : ImageService {
        return Retrofit.Builder()
            .baseUrl(IMAGE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageService::class.java)
    }

    /**
     *  baseUrl은 임시 url -> Interceptor로 변경 예정
     */
    @Singleton
    @Provides
    fun providePreSignedImageService(
        imageUrlInterceptor: ImageUrlInterceptor
    ) : PreSignedImageService {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(imageUrlInterceptor).addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .baseUrl("http://localhost/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreSignedImageService::class.java)
    }
}