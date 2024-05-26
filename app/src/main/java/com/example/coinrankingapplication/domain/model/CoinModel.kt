package com.example.coinrankingapplication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.coinrankingapplication.core.Constants

@Entity(tableName = Constants.DB_NAME)
data class CoinModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: String,
    val change: String,
    var isFavourite: Boolean = false
)