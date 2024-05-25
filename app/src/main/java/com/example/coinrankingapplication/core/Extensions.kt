package com.example.coinrankingapplication.core

import android.annotation.SuppressLint
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

fun ImageView.loadUrl(url: String) {
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