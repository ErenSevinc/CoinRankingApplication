package com.example.coinrankingapplication.core.uiState

import com.example.coinrankingapplication.domain.model.CoinModel


sealed class CoinDetailUIState {
    data class Loading(val isLoading: Boolean): CoinDetailUIState()
    data class Error(val errorMessage: String): CoinDetailUIState()
    data class CoinSuccess (val coin: CoinModel): CoinDetailUIState()
}