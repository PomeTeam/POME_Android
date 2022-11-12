package com.teampome.pome.di

import com.teampome.pome.repository.AddFriendsDataSource
import com.teampome.pome.repository.AddFriendsRepository
import com.teampome.pome.repository.AddFriendsTestDataSource
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
}