package com.example.coinrankingapplication.data.network

import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.core.Constants
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("coins")
    suspend fun getCoinList(
        @Query("timePeriod") timePeriod: String? = Constants.H24,
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): BaseApiResponse<CoinListResponseModel>

    @GET("coin/Qwsogvtv82FCd")
    suspend fun getCoinDetail(): BaseApiResponse<CoinDetailResponseModel>
}