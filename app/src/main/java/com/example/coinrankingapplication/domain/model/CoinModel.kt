package com.example.coinrankingapplication.domain.model

data class CoinModel(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: String,
    val change: String
)
