package com.teampome.pome.di

import com.teampome.pome.network.ImageService
import com.teampome.pome.network.RegisterService
import com.teampome.pome.repository.friend.AddFriendsDataSource
import com.teampome.pome.repository.friend.AddFriendsRepository
import com.teampome.pome.repository.friend.AddFriendsTestDataSource
import com.teampome.pome.repository.record.RecordDataSource
import com.teampome.pome.repository.record.RecordRepository
import com.teampome.pome.repository.record.RecordTestDataSource
import com.teampome.pome.repository.register.*
import com.teampome.pome.repository.remind.RemindDataSource
import com.teampome.pome.repository.remind.RemindRepository
import com.teampome.pome.repository.remind.RemindTestDataSource
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
    fun provideAddFriendsDataSource() : AddFriendsDataSource {
        return AddFriendsTestDataSource() // 이렇게 객체로 넣는게 맞는걸까?
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
    fun provideTestRecordDataSource() : RecordDataSource {
        return RecordTestDataSource()
    }

    @Provides
    @Singleton
    fun provideRecordRepository(dataSource: RecordDataSource) : RecordRepository {
        return RecordRepository(dataSource)
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
}