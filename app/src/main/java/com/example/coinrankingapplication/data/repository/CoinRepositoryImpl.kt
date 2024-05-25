package com.example.coinrankingapplication.data.repository

import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.data.network.ApiService
import com.example.coinrankingapplication.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val apiService: ApiService): CoinRepository {
    override suspend fun getCoinList(): BaseApiResponse<CoinListResponseModel> {
        return apiService.getCoinList()
    }

    override suspend fun getCoinDetail(): BaseApiResponse<CoinDetailResponseModel> {
        return apiService.getCoinDetail()
    }
}