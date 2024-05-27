package com.example.coinrankingapplication.core.base

import com.google.gson.annotations.SerializedName

class BaseApiResponse<T>(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("type")
    val errorType: String? = null,
    @SerializedName("message")
    val errorMessage: String? = null,
)