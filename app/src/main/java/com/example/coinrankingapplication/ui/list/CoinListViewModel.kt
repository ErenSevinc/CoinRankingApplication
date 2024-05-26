package com.example.coinrankingapplication.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.coinrankingapplication.core.Constants
import com.example.coinrankingapplication.core.Constants.H24
import com.example.coinrankingapplication.data.local.LocalRepository
import com.example.coinrankingapplication.domain.model.CoinModel
import com.example.coinrankingapplication.domain.useCase.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
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

    var selectedTime = H24

    suspend fun getCoinList(timePeriod: String): Flow<PagingData<CoinModel>> {
        selectedTime = timePeriod
        val response = coinListUseCase.execute(timePeriod).cachedIn(viewModelScope)
        response.map {
            it.map {model ->
                if (_favCoins.value?.isEmpty() == true) {
                    model.isFavourite = false
                } else {
                    _favCoins.value?.forEach {fav->
                        if (fav.uuid == model.uuid) {
                            model.isFavourite = true
                        }
                    }
                }!!
            }
        }
        return response

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

    private fun getFavCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            _favCoins.postValue(localRepository.getFavCoins())
        }
    }
}
