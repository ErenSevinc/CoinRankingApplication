package com.example.coinrankingapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinrankingapplication.core.uiState.CoinDetailUIState
import com.example.coinrankingapplication.core.utils.Constants
import com.example.coinrankingapplication.core.base.Resource
import com.example.coinrankingapplication.core.utils.getLowAndHigh
import com.example.coinrankingapplication.data.local.LocalRepository
import com.example.coinrankingapplication.data.model.mapper.toCoinModel
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.useCase.CoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinDetailUseCase: CoinDetailUseCase,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _favCoins = MutableLiveData(emptyList<CoinModel>())

    private val _state = MutableLiveData<CoinDetailUIState>()
    val state: LiveData<CoinDetailUIState> = _state

    private var selectedPeriod = Constants.H24

    fun getCoinDetail(coinId: String, timePeriod: String = Constants.H24) {
        selectedPeriod = timePeriod
        viewModelScope.launch {
            coinDetailUseCase.execute(coinId, selectedPeriod).collect {
                when (it) {
                    is Resource.Error -> {
                        _state.value = CoinDetailUIState.Error(it.errorMessage ?: "Error")
                    }

                    is Resource.Loading -> {
                        _state.value = CoinDetailUIState.Loading(true)
                    }

                    is Resource.Success -> {
                        it.result?.data?.coin?.let { item ->
                            val lowAndHigh = item.sparkline?.getLowAndHigh() ?: Pair("", "")
                            val favItem: CoinModel? = _favCoins.value?.find { it.uuid == item.uuid }
                            val coin = item.toCoinModel(
                                isFav = favItem?.isFavourite ?: false,
                                low = lowAndHigh.first,
                                favPrice = favItem?.price ?: "",
                                high = lowAndHigh.second
                            )
                            _state.value = CoinDetailUIState.CoinSuccess(coin)
                        }
                    }
                }
            }
        }
    }

    fun insertCoin(coinModel: CoinModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredArticles = _favCoins.value?.firstOrNull {
                it.uuid == coinModel.uuid
            }
            if (filteredArticles == null) {
                coinModel.isFavourite = true
                localRepository.insert(coinModel)
            }
        }
        getFavCoins()
    }

    fun deleteCoin(coinModel: CoinModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _favCoins.value?.forEach {
                if (it.uuid == coinModel.uuid) {
                    coinModel.isFavourite = false
                    localRepository.delete(it)
                }
            }
        }
        getFavCoins()
    }

    fun getFavCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            _favCoins.postValue(localRepository.getFavCoins())
        }
    }
}