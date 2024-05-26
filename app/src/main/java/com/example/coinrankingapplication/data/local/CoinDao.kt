package com.example.coinrankingapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinrankingapplication.domain.model.CoinModel

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: CoinModel)

    @Delete
    fun delete(coin: CoinModel)

    @Query("SELECT * FROM coins")
    fun getFavCoins(): List<CoinModel>
}
