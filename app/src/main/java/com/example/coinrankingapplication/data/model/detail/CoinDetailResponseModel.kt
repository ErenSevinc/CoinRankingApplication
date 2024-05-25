package com.example.coinrankingapplication.data.model.detail

import com.google.gson.annotations.SerializedName

data class CoinDetailResponseModel(
    @SerializedName("coin")
    val coin: Coin? = null
)

data class Coin(
    val uuid: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val description: String? = null,
    val color: String? = null,
    val iconUrl: String? = null,
    val websiteUrl: String? = null,
    @SerializedName("links")
    val links: List<Link>? = emptyList(),
    val supply: Supply? = null,
    val numberOfMarkets: Long? = null,
    val numberOfExchanges: Long? = null,
    @SerializedName("24hVolume")
    val h24Volume: String? = null,
    val marketCap: String? = null,
    val fullyDilutedMarketCap: String? = null,
    val price: String? = null,
    val btcPrice: String? = null,
    val priceAt: Long? = null,
    val change: String? = null,
    val rank: Long? = null,
    val sparkline: List<String?>? = emptyList(),
    @SerializedName("allTimeHigh")
    val allTimeHigh: AllTimeHigh? = null,
    @SerializedName("coinrankingUrl")
    val coinRankingUrl: String? = null,
    val tier: Long? = null,
    val lowVolume: Boolean? = null,
    val listedAt: Long? = null,
    val hasContent: Boolean? = null,
    @SerializedName("notices")
    val notices: List<Notice>? = emptyList(),
    val contractAddresses: List<String>? = emptyList(),
    val tags: List<String>? = null,
)

data class Link(
    val name: String? = null,
    val url: String? = null,
    val type: String? = null,
)

data class Supply(
    val confirmed: Boolean? = null,
    val supplyAt: Long? = null,
    val max: String? = null,
    val total: String? = null,
    val circulating: String? = null,
)

data class AllTimeHigh(
    val price: String? = null,
    val timestamp: Long? = null,
)

data class Notice(
    val type: String? = null,
    @SerializedName("value")
    val value: String? = null
)
