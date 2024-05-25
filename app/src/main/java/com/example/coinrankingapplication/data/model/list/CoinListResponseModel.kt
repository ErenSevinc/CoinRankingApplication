package com.example.coinrankingapplication.data.model.list

import com.google.gson.annotations.SerializedName

data class CoinListResponseModel(
    @SerializedName("stats")
    val stats: Stats? = null,
    @SerializedName("coins")
    val coins: List<CoinItem>? = emptyList()
)

data class Stats(
    val total: Long? = null,
    val totalCoins: Long? = null,
    val totalMarkets: Long? = null,
    val totalExchanges: Long? = null,
    val totalMarketCap: String? = null,
    val total24hVolume: String? = null,
)

data class CoinItem(
    val uuid: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val color: String? = null,
    val iconUrl: String? = null,
    val marketCap: String? = null,
    val price: String? = null,
    val listedAt: Long? = null,
    val tier: Long? = null,
    val change: String? = null,
    val rank: Long? = null,
    val sparkline: List<String?>? = emptyList(),
    val lowVolume: Boolean? = null,
    val coinrankingUrl: String? = null,
    @SerializedName("24hVolume")
    val h24Volume: String? = null,
    val btcPrice: String? = null,
    val contractAddresses: List<String>? = emptyList(),
)