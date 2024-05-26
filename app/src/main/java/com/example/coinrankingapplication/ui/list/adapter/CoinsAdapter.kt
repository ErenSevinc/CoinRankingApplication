package com.example.coinrankingapplication.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrankingapplication.core.isFavMatch
import com.example.coinrankingapplication.core.loadUrl
import com.example.coinrankingapplication.core.setColor
import com.example.coinrankingapplication.core.toDoublePrice
import com.example.coinrankingapplication.databinding.ItemCoinBinding
import com.example.coinrankingapplication.domain.model.CoinModel
import javax.inject.Inject

class CoinsAdapter @Inject constructor(private val onClick: (id: String) -> Unit, private val onFavClick: (item: CoinModel) -> Unit) :
    PagingDataAdapter<CoinModel, CoinsAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(
        private val binding: ItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinModel, onClick: (id: String) -> Unit, onFavClick: (item: CoinModel) -> Unit) {
            with(binding) {
                coinName.text= item.name
                coinSymbol.text = item.symbol
                coinPrice.text = "$ "+ item.price.toDoublePrice()
                coinBenefit.setTextColor(ContextCompat.getColor(root.context, item.setColor()))
                coinBenefit.text = "% "+ item.change
                coinImage.loadUrl(item.iconUrl)
                favImage.setBackgroundResource(item.isFavourite.isFavMatch())

                favImage.setOnClickListener {
                    favImage.setBackgroundResource(item.isFavourite.isFavMatch())
                    onFavClick.invoke(item)
                }
                root.setOnClickListener {
                    onClick.invoke(item.uuid)
                }
            }
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
        getItem(position)?.let {
            holder.bind(it, onClick, onFavClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}