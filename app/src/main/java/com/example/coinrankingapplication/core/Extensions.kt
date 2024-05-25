package com.example.coinrankingapplication.core

import android.annotation.SuppressLint
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun ImageView.loadUrl(url: String?) {
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()

    val request = ImageRequest.Builder(context)
        .crossfade(true)
        .crossfade(500)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

@SuppressLint("DefaultLocale")
fun String.toDoublePrice(): String {
    val price = this.toDouble()
    return String.format("%.4f", price)
}

fun AutoCompleteTextView.showDropdown(adapter: ArrayAdapter<String>?) {
    if(!TextUtils.isEmpty(this.text.toString())){
        adapter?.filter?.filter(null)
    }
}