package com.example.coinrankingapplication.core

import android.annotation.SuppressLint
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinrankingapplication.R
import com.example.coinrankingapplication.domain.model.CoinModel
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

fun List<String?>.getLowAndHigh(): Pair<String, String> {
    val list = emptyList<Double>().toMutableList()
    this.forEach {
        if (!it.isNullOrEmpty()) {
            list.add(it.toDouble())
        }
    }
    list.sort()
    return Pair(
        first = list.first().toString().toDoublePrice(),
        second = list.last().toString().toDoublePrice()
    )
}

fun AutoCompleteTextView.showDropdown(adapter: ArrayAdapter<String>?) {
    if (!TextUtils.isEmpty(this.text.toString())) {
        adapter?.filter?.filter(null)
    }
}

fun Boolean.isFavMatch(): Int {
    return if (this) {
        R.drawable.ic_star
    } else {
        R.drawable.ic_star_border
    }
}

fun CoinModel.setColor(): Int {
    return if (this.change.contains("-")) {
        R.color.red
    } else {
        R.color.green
    }
}