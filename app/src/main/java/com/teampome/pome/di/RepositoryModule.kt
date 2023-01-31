package com.teampome.pome.di

import com.teampome.pome.network.*
import com.teampome.pome.repository.friend.AddFriendsDataSource
import com.teampome.pome.repository.friend.AddFriendsRemoteDataSource
import com.teampome.pome.repository.friend.AddFriendsRepository
import com.teampome.pome.repository.login.LoginDataSource
import com.teampome.pome.repository.login.LoginRemoteDataSource
import com.teampome.pome.repository.login.LoginRepository
import com.teampome.pome.repository.record.RecordDataSource
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.repository.record.RecordTestDataSource
import com.teampome.pome.repository.register.*
import com.teampome.pome.repository.remind.RemindDataSource
import com.teampome.pome.repository.remind.RemindRepository
import com.teampome.pome.repository.remind.RemindTestDataSource
import com.teampome.pome.util.token.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAddFriendsDataSource(service: AddFriendsService) : AddFriendsDataSource {
        return AddFriendsRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideAddFriendsRepository(dataSource: AddFriendsDataSource) : AddFriendsRepository {
        return AddFriendsRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideTestRemindDataSource() : RemindDataSource {
        return RemindTestDataSource()
    }

    @Provides
    @Singleton
    fun provideTestRemindRepository(dataSource: RemindDataSource) : RemindRepository {
        return RemindRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideTestRecordDataSource(service: RecordService) : RecordDataSource {
        return RecordTestDataSource(service)
    }

    @Provides
    @Singleton
    fun provideRecordRepository(dataSource: RecordDataSource, userManager: UserManager) : RecordRepository {
        return RecordRepository(dataSource, userManager)
    }

    @Provides
    @Singleton
    fun provideRegisterDataSource(service: RegisterService) : RegisterDataSource {
        return RegisterRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(dataSource: RegisterDataSource) : RegisterRepository {
        return RegisterRepository(dataSource)
    }

    @Provides
    @Singleton
    fun providePresignedUrlDataSource(service: ImageService) : PresignedUrlDataSource {
        return PresignedUrlRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun providePresignedUrlRepository(dataSource: PresignedUrlDataSource) : PresignedUrlRepository {
        return PresignedUrlRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideSendPreSignedImageDataSource(service: PreSignedImageService) : SendPreSignedImageDataSource {
        return SendPreSignedImageRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideSendPreSignedImageRepository(dataSource: SendPreSignedImageDataSource) : SendPreSignedImageRepository {
        return SendPreSignedImageRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource(service: LoginService) : LoginDataSource {
        return LoginRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(dataSource: LoginDataSource) : LoginRepository {
        return LoginRepository(dataSource)
    }
}