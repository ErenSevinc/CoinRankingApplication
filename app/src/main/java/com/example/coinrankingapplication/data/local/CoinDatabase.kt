package com.example.coinrankingapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coinrankingapplication.domain.model.CoinModel

@Database(
    entities = [CoinModel::class],
    version = 1,
    exportSchema = false
)
abstract class CoinDatabase: RoomDatabase() {
    abstract fun dao(): CoinDao
}

