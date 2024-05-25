package com.example.coinrankingapplication.domain.repository

import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel

interface CoinRepository {

    suspend fun getCoinList(): BaseApiResponse<CoinListResponseModel>
    suspend fun getCoinDetail(): BaseApiResponse<CoinDetailResponseModel>
}