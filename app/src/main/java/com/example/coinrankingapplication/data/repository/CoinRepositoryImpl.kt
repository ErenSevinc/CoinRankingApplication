package com.example.coinrankingapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinrankingapplication.core.base.BaseApiResponse
import com.example.coinrankingapplication.data.dataSource.CoinDataSource
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.data.repository.paging.CoinPagingSource
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val dataSource: CoinDataSource): CoinRepository {

    override suspend fun getCoinList(timePeriod: String, offset: Int): BaseApiResponse<CoinListResponseModel> {
        return dataSource.getCoinList(timePeriod, offset)
    }

    override suspend fun getCoinDetail(coinId: String, timePeriod: String): BaseApiResponse<CoinDetailResponseModel> {
        return dataSource.getCoinDetail(coinId, timePeriod)
    }
}