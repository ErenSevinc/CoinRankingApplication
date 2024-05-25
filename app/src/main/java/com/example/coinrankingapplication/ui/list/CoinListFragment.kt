package com.example.coinrankingapplication.ui.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.coinrankingapplication.R
import com.example.coinrankingapplication.core.CoinListUIState
import com.example.coinrankingapplication.databinding.FragmentCoinListBinding
import com.example.coinrankingapplication.domain.model.CoinModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private val viewModel: CoinListViewModel by viewModels()
    private var binding: FragmentCoinListBinding? = null
    private var adapter: CoinAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinListBinding.inflate(layoutInflater, container, false)

        viewModel.getCoinList()
        setupObservers()

        return binding?.root ?: View(context)
    }

    private fun setupRecyclerView() {
        adapter = CoinAdapter()
        binding?.list?.adapter = adapter

    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is CoinListUIState.CoinList -> {
                    Log.e("DATA", it.data.toString())
                    recyclerViewVisibility(it.data)
                }
                is CoinListUIState.Error -> {
                   errorTextVisibility(it.errorMessage)
                }
                is CoinListUIState.Loading -> {
                    loaderVisibility(it.isLoading)
                }
            }
        }
    }

    private fun errorTextVisibility(errorMessage: String) {
        binding?.apply {
            loading.isVisible = false
            list.isVisible = false
            errorText.isVisible = true

            errorText.text = errorMessage
        }
    }

    private fun loaderVisibility(isLoader: Boolean) {
        binding?.apply {
            loading.isVisible = isLoader
            list.isVisible = !isLoader
            errorText.isVisible = !isLoader
        }
    }

    private fun recyclerViewVisibility(coins: List<CoinModel>) {
        binding?.apply {
            loading.isVisible = false
            list.isVisible = true
            errorText.isVisible = false

            setupRecyclerView()
            adapter?.submitList(coins)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }
}