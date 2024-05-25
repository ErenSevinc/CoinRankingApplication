package com.example.coinrankingapplication.core.di

import com.example.coinrankingapplication.core.Constants.BASE_URL
import com.example.coinrankingapplication.core.MyInterceptor
import com.example.coinrankingapplication.data.dataSource.CoinDataSource
import com.example.coinrankingapplication.data.dataSource.CoinDataSourceImpl
import com.example.coinrankingapplication.data.network.ApiService
import com.example.coinrankingapplication.data.repository.CoinRepositoryImpl
import com.example.coinrankingapplication.domain.repository.CoinRepository
import com.example.coinrankingapplication.domain.useCase.CoinListUseCase
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
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesCoinDataSource(
        api: ApiService
    ): CoinDataSource {
        return CoinDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun providesCoinRepository(
        coinDataSource: CoinDataSource
    ): CoinRepository {
        return CoinRepositoryImpl(coinDataSource)
    }

    @Singleton
    @Provides
    fun providesCoinListUseCase(
        coinRepository: CoinRepository
    ): CoinListUseCase {
        return CoinListUseCase(coinRepository)
    }
}