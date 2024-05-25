package com.example.coinrankingapplication.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinrankingapplication.core.CoinListUIState
import com.example.coinrankingapplication.core.Resource
import com.example.coinrankingapplication.data.model.mapper.toCoinModel
import com.example.coinrankingapplication.data.model.mapper.toCoinModelList
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.useCase.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinListUseCase: CoinListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CoinListUIState>()
    val state: LiveData<CoinListUIState> = _state

    private val itemList = mutableListOf<CoinModel>()

    fun getCoinList() {
        viewModelScope.launch {
            coinListUseCase.execute().collect {
                when (it) {
                    is Resource.Error -> {
                        _state.value = CoinListUIState.Error(it.errorMessage ?: "Error")
                    }

                    is Resource.Loading -> {
                        _state.value = CoinListUIState.Loading(true)
                    }
                    is Resource.Success -> {
                        it.data?.data?.coins?.let {list ->
                            itemList.addAll(list.toCoinModelList())
                        }
                        _state.value = CoinListUIState.CoinList(itemList)
                    }
                }
            }
        }
    }
}