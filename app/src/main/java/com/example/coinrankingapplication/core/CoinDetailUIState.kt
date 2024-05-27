package com.example.coinrankingapplication.core

import com.example.coinrankingapplication.data.model.detail.Coin
import com.example.coinrankingapplication.domain.model.CoinModel


sealed class CoinDetailUIState {
    data class Loading(val isLoading: Boolean): CoinDetailUIState()
    data class Error(val errorMessage: String): CoinDetailUIState()
    data class CoinSuccess (val coin: CoinModel): CoinDetailUIState()
}