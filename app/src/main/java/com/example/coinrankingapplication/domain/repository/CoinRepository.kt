package com.example.coinrankingapplication.domain.repository

import androidx.paging.PagingData
import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.domain.model.CoinModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getCoinList(timePeriod: String): Flow<PagingData<CoinModel>>
    suspend fun getCoinDetail(coinId: String, timePeriod: String): BaseApiResponse<CoinDetailResponseModel>
}