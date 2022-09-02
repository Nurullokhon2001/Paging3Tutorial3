package com.example.paging3tutorial.di

import android.content.Context
import androidx.room.Room
import com.example.paging3tutorial.data.local.RickyDb
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

}