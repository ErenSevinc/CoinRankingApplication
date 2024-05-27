package com.example.coinrankingapplication.data.model.mapper

import com.example.coinrankingapplication.data.model.detail.Coin
import com.example.coinrankingapplication.data.model.list.CoinItem
import com.example.coinrankingapplication.domain.model.CoinModel

fun CoinItem.toCoinModel(): CoinModel {
    return CoinModel(
        uuid = this.uuid ?: "",
        symbol = this.symbol ?: "",
        name = this.name ?: "",
        iconUrl = this.iconUrl ?: "",
        price = this.price ?: "",
        change = this.change ?: ""
    )
}

fun List<CoinItem>.toCoinModelList(): List<CoinModel> {
    return this.map {
        it.toCoinModel()
    }
}

fun Coin.toCoinModel(isFav:Boolean, favPrice: String, low:String, high:String): CoinModel {
    return CoinModel(
        uuid = this.uuid ?: "",
        symbol = this.symbol ?: "",
        name = this.name ?: "",
        iconUrl = this.iconUrl ?: "",
        price = this.price ?: "",
        change = this.change ?: "",
        lowPrice = low,
        highPrice = high,
        favPrice = favPrice,
        isFavourite = isFav
    )
}