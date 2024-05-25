package com.example.coinrankingapplication.core

import com.example.coinrankingapplication.domain.model.CoinModel

sealed class CoinListUIState {
    data class Loading(val isLoading: Boolean): CoinListUIState()
    data class Error(val errorMessage: String): CoinListUIState()
    data class CoinList (val data: List<CoinModel>): CoinListUIState()
}