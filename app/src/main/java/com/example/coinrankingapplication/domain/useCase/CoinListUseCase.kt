package com.example.coinrankingapplication.domain.useCase

import androidx.paging.PagingData
import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.core.Resource
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinListUseCase @Inject constructor(private val coinRepository: CoinRepository) {

    suspend fun execute(timePeriod: String): Flow<PagingData<CoinModel>>  {
        return coinRepository.getCoinList(timePeriod)
    }

}