package com.example.paging3tutorial.di

import android.content.Context
import androidx.room.Room
import com.example.paging3tutorial.LocalRepositoryImpl
import com.example.paging3tutorial.data.local.RickyDb
import com.example.paging3tutorial.data.local.dao.RemoteKeysDao
import com.example.paging3tutorial.data.local.dao.ResultEntityDao
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.LocalRepository
import com.example.paging3tutorial.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDi {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RickyDb::class.java,
        "ricky_db"
    ).build()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(rickyDb: RickyDb) = rickyDb.remoteKeysDao()

    @Singleton
    @Provides
    fun provideReposDao(rickyDb: RickyDb) = rickyDb.reposDao()

    @Singleton
    @Provides
    fun provideLocalRepository(
        remoteKeysDao: RemoteKeysDao,
        reposDao: ResultEntityDao
    ): LocalRepository = LocalRepositoryImpl(remoteKeysDao, reposDao)

    @Singleton
    @Provides
    fun provideClearRemoteKeysDao(localRepository: LocalRepository) =
        ClearRemoteKeysDao(localRepository)

    @Singleton
    @Provides
    fun provideClearReposUseCaseDao(localRepository: LocalRepository) =
        ClearReposUseCase(localRepository)

    @Singleton
    @Provides
    fun provideGetAllDataUseCaseDao(localRepository: LocalRepository) =
        GetAllDataUseCase(localRepository)

    @Singleton
    @Provides
    fun provideInsertAllUseCaseDao(localRepository: LocalRepository) =
        InsertAllUseCase(localRepository)

    @Singleton
    @Provides
    fun provideInsertRemoteKeysUseCaseDao(localRepository: LocalRepository) =
        InsertRemoteKeysUseCase(localRepository)

    @Singleton
    @Provides
    fun provideRemoteKeysRepoIdUseCaseDao(localRepository: LocalRepository) =
        RemoteKeysRepoIdUseCase(localRepository)

}