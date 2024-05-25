package com.example.coinrankingapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.data.dataSource.CoinDataSource
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.data.network.ApiService
import com.example.coinrankingapplication.data.repository.paging.CoinPagingSource
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val dataSource: CoinDataSource): CoinRepository {
    override suspend fun getCoinList(timePeriod: String): Flow<PagingData<CoinModel>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                CoinPagingSource(dataSource, timePeriod)
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    override suspend fun getCoinDetail(): BaseApiResponse<CoinDetailResponseModel> {
        return dataSource.getCoinDetail()
    }
}