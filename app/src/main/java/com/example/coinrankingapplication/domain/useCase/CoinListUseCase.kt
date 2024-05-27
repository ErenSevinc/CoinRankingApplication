package com.example.coinrankingapplication.domain.useCase

import com.example.coinrankingapplication.core.base.BaseApiResponse
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinListUseCase @Inject constructor(private val coinRepository: CoinRepository) {

    fun execute(timePeriod: String, offset: Int): Flow<BaseApiResponse<CoinListResponseModel>> = flow {
        emit(coinRepository.getCoinList(timePeriod, offset))
    }
}