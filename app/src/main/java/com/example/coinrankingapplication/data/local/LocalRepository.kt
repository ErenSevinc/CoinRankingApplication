package com.example.coinrankingapplication.data.local

import com.example.coinrankingapplication.domain.model.CoinModel
import javax.inject.Inject

class LocalRepository  @Inject constructor(
    private val dao: CoinDao
) {
    fun insert(coinModel: CoinModel) = dao.insert(coinModel)
    fun delete(coinModel: CoinModel) = dao.delete(coinModel)
    fun getFavCoins(): List<CoinModel> = dao.getFavCoins()

}