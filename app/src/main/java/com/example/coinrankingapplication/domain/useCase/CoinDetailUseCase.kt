package com.example.coinrankingapplication.domain.useCase

import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.core.Resource
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class CoinDetailUseCase @Inject constructor(private val repository: CoinRepository) {

    fun execute(coinId: String, timePeriod: String): Flow<Resource<BaseApiResponse<CoinDetailResponseModel>>> = flow {
        try {
            emit(Resource.Loading)
            val movieDetail = repository.getCoinDetail(coinId, timePeriod)
            emit(Resource.Success(movieDetail))
        } catch (e: IOError) {
            emit(Resource.Error(errorMessage = "No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(errorMessage = e.localizedMessage ?: "Error"))
        }
    }
}