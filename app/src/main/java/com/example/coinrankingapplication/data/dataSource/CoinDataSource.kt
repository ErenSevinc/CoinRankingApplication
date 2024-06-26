package com.example.coinrankingapplication.data.dataSource

import com.example.coinrankingapplication.core.base.BaseApiResponse
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel

interface CoinDataSource {
    suspend fun getCoinList(timePeriod: String, offset: Int): BaseApiResponse<CoinListResponseModel>
    suspend fun getCoinDetail(coinId: String, timePeriod: String): BaseApiResponse<CoinDetailResponseModel>
}