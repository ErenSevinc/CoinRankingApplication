package com.example.coinrankingapplication.core.di

import com.example.coinrankingapplication.core.Constants.BASE_URL
import com.example.coinrankingapplication.core.MyInterceptor
import com.example.coinrankingapplication.data.network.ApiService
import com.example.coinrankingapplication.data.repository.CoinRepositoryImpl
import com.example.coinrankingapplication.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideMyInterceptor(): MyInterceptor {
        return MyInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
       httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: ApiService): CoinRepository {
        return CoinRepositoryImpl(apiService = api)
    }
}