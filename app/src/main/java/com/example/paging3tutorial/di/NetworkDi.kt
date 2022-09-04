package com.example.paging3tutorial.di

import com.example.paging3tutorial.data.NetworkRepositoryImpl
import com.example.paging3tutorial.data.network.RickyAndMortyApi
import com.example.paging3tutorial.data.network.RickyAndMortyApi.Companion.BASE_URL_API
import com.example.paging3tutorial.domain.NetworkRepository
import com.example.paging3tutorial.domain.use_case.GetCharactersByNameUseCase
import com.example.paging3tutorial.domain.use_case.GetCharactersUseCase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkDi {

    @Provides
    @Singleton
    fun provideRickyAndMortyApi(httpLoggingInterceptor: OkHttpClient): RickyAndMortyApi =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .baseUrl(BASE_URL_API)
            .client(httpLoggingInterceptor)
            .build()
            .create(RickyAndMortyApi::class.java)

    @Provides
    fun providesHttpLoggingInterceptor() = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides
    @Singleton
    fun provideNetworkRepository(rickyAndMortyApi: RickyAndMortyApi): NetworkRepository =
        NetworkRepositoryImpl(rickyAndMortyApi)

    @Provides
    fun provideGetCharactersUseCase(networkRepository: NetworkRepository) =
        GetCharactersUseCase(networkRepository)

    @Provides
    fun provideGetCharactersByNameUseCase(networkRepository: NetworkRepository) =
        GetCharactersByNameUseCase(networkRepository)

}