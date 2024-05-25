package com.example.coinrankingapplication.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrankingapplication.core.loadUrl
import com.example.coinrankingapplication.core.toDoublePrice
import com.example.coinrankingapplication.databinding.ItemCoinBinding
import com.example.coinrankingapplication.domain.model.CoinModel
import javax.inject.Inject

class CoinsAdapter @Inject constructor() :
    PagingDataAdapter<CoinModel, CoinsAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(
        private val binding: ItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinModel?) {
            binding.coinName.text= item?.name
            binding.coinSymbol.text = item?.symbol
            binding.coinPrice.text = item?.price?.toDoublePrice()
            binding.coinBenefit.text = item?.change
            binding.coinImage.loadUrl(item?.iconUrl)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CoinModel>() {
            override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel) = oldItem === newItem
            override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel) =
                oldItem.uuid == newItem.uuid
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}