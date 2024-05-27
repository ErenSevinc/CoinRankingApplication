package com.example.coinrankingapplication.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.coinrankingapplication.core.utils.Constants.H24
import com.example.coinrankingapplication.data.local.LocalRepository
import com.example.coinrankingapplication.data.model.mapper.toCoinModelList
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.useCase.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val coinListUseCase: CoinListUseCase
) : ViewModel() {

    private val _favCoins = MutableLiveData(emptyList<CoinModel>())
    val favCoin: LiveData<List<CoinModel>> = _favCoins

    private val _coins: MutableLiveData<List<CoinModel>> = MutableLiveData()
    val coins: LiveData<List<CoinModel>> = _coins

    private val coinList: MutableList<CoinModel> = mutableListOf()
    private var page = 0
    private var totalPages = -1L
    private var selectedTime = H24

    init {
        fetchData()
    }

    fun refreshData(timePeriod: String) {
        coinList.clear()
        page = 0
        selectedTime = timePeriod
        fetchData()
    }

    fun fetchData() {
        if (totalPages == -1L || page < totalPages) {
            viewModelScope.launch {
                coinListUseCase.execute(selectedTime, page).collect { response ->
                    response.data?.coins?.let { responseList ->
                        responseList.toCoinModelList().forEach { coinModel ->
                            if (_favCoins.value?.isEmpty() == true) {
                                coinModel.isFavourite = false
                            } else {
                                _favCoins.value?.forEach { favCoinModel ->
                                    if (favCoinModel.uuid == coinModel.uuid) {
                                        coinModel.isFavourite = true
                                    }
                                }
                            }
                            coinList.add(coinModel)
                        }
                        _coins.value = coinList.toMutableList()
                        totalPages = response.data.stats?.totalCoins ?: -1
                    }
                }
                page += 20
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
            getFavCoins()
            refreshData(selectedTime)
        }
    }

    fun deleteCoin(coinModel: CoinModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _favCoins.value?.forEach {
                if (it.uuid == coinModel.uuid) {
                    coinModel.isFavourite = false
                    localRepository.delete(it)
                }
            }
            getFavCoins()
            refreshData(selectedTime)
        }
    }

    private fun getFavCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            _favCoins.postValue(localRepository.getFavCoins())
        }
    }
}