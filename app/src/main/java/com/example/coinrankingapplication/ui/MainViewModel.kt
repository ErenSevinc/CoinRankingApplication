package com.example.coinrankingapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _visibility = MutableLiveData<Boolean>()
    val visibility: LiveData<Boolean> = _visibility
    fun setToolbarTitle(title: String) {
        _title.value = title
    }

    fun setToolbarVisibility(isVisible: Boolean) {
        _visibility.value = isVisible
    }
}