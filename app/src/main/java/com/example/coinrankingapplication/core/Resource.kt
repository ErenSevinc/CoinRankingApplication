package com.example.coinrankingapplication.core

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Error(val errorMessage: String?): Resource<Nothing>()
    data class Success<out T>(val result: T?): Resource<T>()
}