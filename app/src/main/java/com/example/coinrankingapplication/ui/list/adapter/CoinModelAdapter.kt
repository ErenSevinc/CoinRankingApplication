package com.example.coinrankingapplication.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrankingapplication.core.utils.isFavMatch
import com.example.coinrankingapplication.core.utils.loadUrl
import com.example.coinrankingapplication.core.utils.setColor
import com.example.coinrankingapplication.core.utils.toDoublePrice
import com.example.coinrankingapplication.databinding.ItemCoinBinding
import com.example.coinrankingapplication.domain.model.CoinModel
import javax.inject.Inject

class CoinModelAdapter @Inject constructor(
    private val onClick: (id: String) -> Unit,
    private val onFavClick: (item: CoinModel) -> Unit
) : ListAdapter<CoinModel, CoinModelAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClick, onFavClick)
    }

    override fun getItemCount() = currentList.size

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
}