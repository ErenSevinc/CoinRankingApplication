package com.example.coinrankingapplication.domain.repository

import com.example.coinrankingapplication.core.base.BaseApiResponse
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel

interface CoinRepository {
    suspend fun getCoinList(timePeriod: String, offset: Int): BaseApiResponse<CoinListResponseModel>
    suspend fun getCoinDetail(coinId: String, timePeriod: String): BaseApiResponse<CoinDetailResponseModel>
}