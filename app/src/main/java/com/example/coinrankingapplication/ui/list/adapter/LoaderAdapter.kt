package com.example.coinrankingapplication.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrankingapplication.databinding.ItemLoaderBinding

class LoaderAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(private val binding: ItemLoaderBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvError.text = loadState.error.localizedMessage
            }

            binding.loading.isVisible = loadState is LoadState.Loading
            binding.tvError.isVisible = loadState !is LoadState.Loading
            binding.buttonRetry.isVisible = loadState !is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoaderBinding.inflate(inflater, null, false)
        return LoaderViewHolder(binding, retry)
    }
}