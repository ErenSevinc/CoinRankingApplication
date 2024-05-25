package com.example.coinrankingapplication.domain.useCase

import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.core.Resource
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import com.example.coinrankingapplication.domain.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinListUseCase @Inject constructor(private val coinRepository: CoinRepository) {

    fun execute(): Flow<Resource<BaseApiResponse<CoinListResponseModel>>> = flow {
        try {
            emit(Resource.Loading)
            val response = coinRepository.getCoinList()
            if (response.status == "success") {
                emit(Resource.Success(data = response))
            } else {
                val error = response.errorMessage
                emit(Resource.Error(error))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

}