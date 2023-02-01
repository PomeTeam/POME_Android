package com.teampome.pome.di

import android.content.Context
import com.teampome.pome.network.*
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
import javax.inject.Qualifier
import javax.inject.Singleton

const val BASE_URL = "http://52.79.89.129/"
const val IMAGE_BASE_URL = "http://image-main-server.ap-northeast-2.elasticbeanstalk.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ImageRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TempRetrofit

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
    fun provideAuthAuthenticator(tokenManager: TokenManager, userManager: UserManager): AuthAuthenticator = AuthAuthenticator(tokenManager, userManager)

    @Singleton
    @Provides
    fun provideUserUrlInterceptor(userManager: UserManager): ImageUrlInterceptor = ImageUrlInterceptor(userManager)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @BaseRetrofit
    @Singleton
    @Provides
    fun provideBaseRetrofitBuilder() : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @AuthRetrofit
    @Singleton
    @Provides
    fun provideAuthRetrofitBuilder(
        okHttpClient: OkHttpClient
    ) : Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @ImageRetrofit
    @Singleton
    @Provides
    fun provideImageRetrofitBuilder() : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(IMAGE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    /**
     *  baseUrl은 임시 url -> Interceptor로 url변경 예정
     */
    @TempRetrofit
    @Singleton
    @Provides
    fun provideTempRetrofitBuilder(
        imageUrlInterceptor: ImageUrlInterceptor
    ) : Retrofit.Builder {
        return  Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(imageUrlInterceptor)
                .addInterceptor(HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build()
            )
            .baseUrl("http://localhost/")
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideAuthService(@AuthRetrofit retrofit: Retrofit.Builder) : AuthService =
        retrofit
            .build()
            .create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideRegisterService(@BaseRetrofit retrofit: Retrofit.Builder) : RegisterService =
        retrofit
            .build()
            .create(RegisterService::class.java)

    @Singleton
    @Provides
    fun provideImageService(
        @ImageRetrofit retrofit: Retrofit.Builder
    ) : ImageService {
        return retrofit
            .build()
            .create(ImageService::class.java)
    }

    @Singleton
    @Provides
    fun providePreSignedImageService(
        @TempRetrofit retrofit: Retrofit.Builder
    ) : PreSignedImageService {
        return retrofit
            .build()
            .create(PreSignedImageService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginService(
        @BaseRetrofit retrofit: Retrofit.Builder
    ) : LoginService {
        return retrofit
            .build()
            .create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideRecordService(
        @AuthRetrofit retrofit: Retrofit.Builder
    ) : RecordService {
        return retrofit
            .build()
            .create(RecordService::class.java)
    }

    @Singleton
    @Provides
    fun provideAddFriendsService(
        @AuthRetrofit retrofit: Retrofit.Builder
    ) : AddFriendsService {
        return retrofit
            .build()
            .create(AddFriendsService::class.java)
    }

    @Singleton
    @Provides
    fun provideGoalService(
        @AuthRetrofit retrofit: Retrofit.Builder
    ) : GoalService {
        return retrofit
            .build()
            .create(GoalService::class.java)
    }
}