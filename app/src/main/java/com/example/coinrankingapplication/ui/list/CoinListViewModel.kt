package com.example.coinrankingapplication.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.useCase.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinListUseCase: CoinListUseCase
) : ViewModel() {

    suspend fun getCoinList(timePeriod: String): Flow<PagingData<CoinModel>> {
        return coinListUseCase.execute(timePeriod).cachedIn(viewModelScope)
    }
}
