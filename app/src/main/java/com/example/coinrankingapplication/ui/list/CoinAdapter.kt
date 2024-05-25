package com.example.coinrankingapplication.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coinrankingapplication.core.loadUrl
import com.example.coinrankingapplication.core.toDoublePrice
import com.example.coinrankingapplication.databinding.ItemCoinBinding
import com.example.coinrankingapplication.domain.model.CoinModel

class CoinAdapter(): ListAdapter<CoinModel, CoinAdapter.CoinViewHolder>(diffCallBack) {

    class CoinViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoinModel) {
            binding.coinName.text= item.name
            binding.coinSymbol.text = item.symbol
            binding.coinPrice.text = item.price.toDoublePrice()
            binding.coinBenefit.text = item.change
            binding.coinImage.loadUrl(item.iconUrl)

        }
    }


    object diffCallBack: DiffUtil.ItemCallback<CoinModel>(){
        override fun areItemsTheSame(
            oldItem: CoinModel,
            newItem: CoinModel
        ): Boolean  = oldItem === newItem


        override fun areContentsTheSame(
            oldItem: CoinModel,
            newItem: CoinModel
        ): Boolean = oldItem.uuid == newItem.uuid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(inflater, parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}