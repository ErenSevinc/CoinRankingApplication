package com.example.coinrankingapplication.data.dataSource

import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.data.network.ApiService
import javax.inject.Inject

class CoinDataSourceImpl  @Inject constructor(
    private val api: ApiService
) : CoinDataSource {
    override suspend fun getCoinList(
        timePeriod: String,
        offset: Int
    ): BaseApiResponse<CoinListResponseModel> {
        return api.getCoinList(timePeriod, offset)
    }

    override suspend fun getCoinDetail(coinId: String, timePeriod: String): BaseApiResponse<CoinDetailResponseModel> {
        return api.getCoinDetail(coinId, timePeriod)
    }
}