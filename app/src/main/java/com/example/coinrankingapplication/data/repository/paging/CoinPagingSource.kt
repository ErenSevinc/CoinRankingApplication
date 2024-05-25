package com.example.coinrankingapplication.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coinrankingapplication.data.dataSource.CoinDataSource
import com.example.coinrankingapplication.data.model.mapper.toCoinModelList
import com.example.coinrankingapplication.domain.model.CoinModel
import retrofit2.HttpException
import java.io.IOException

class CoinPagingSource(private val dataSource: CoinDataSource, private val timePeriod: String): PagingSource<Int, CoinModel>(){
    override fun getRefreshKey(state: PagingState<Int, CoinModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinModel> {
        return try {
            val currentOffSet = (params.key)?.minus(1) ?: 0
            val response = dataSource.getCoinList(timePeriod, currentOffSet)
            var totalCoins = response.data?.stats?.totalCoins
            LoadResult.Page(
                data = response.data?.coins?.toCoinModelList() ?: emptyList(),
                prevKey = if (currentOffSet ==0) null else currentOffSet.minus(20),
                nextKey = if (response.data?.coins?.isEmpty() == true) null else currentOffSet.plus(20)
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}