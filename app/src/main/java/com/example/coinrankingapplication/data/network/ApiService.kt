package com.example.coinrankingapplication.data.network

import com.example.coinrankingapplication.core.BaseApiResponse
import com.example.coinrankingapplication.data.model.detail.CoinDetailResponseModel
import com.example.coinrankingapplication.data.model.list.CoinListResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {

    @GET("coins")
    suspend fun getCoinList() : BaseApiResponse<CoinListResponseModel>
    @GET("coin/Qwsogvtv82FCd")
    suspend fun getCoinDetail(): BaseApiResponse<CoinDetailResponseModel>
}