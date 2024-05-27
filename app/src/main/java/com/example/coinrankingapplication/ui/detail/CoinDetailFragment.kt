package com.example.coinrankingapplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coinrankingapplication.R
import com.example.coinrankingapplication.core.uiState.CoinDetailUIState
import com.example.coinrankingapplication.core.utils.Constants
import com.example.coinrankingapplication.core.utils.Constants.H24
import com.example.coinrankingapplication.core.utils.isFavMatch
import com.example.coinrankingapplication.core.utils.showDropdown
import com.example.coinrankingapplication.databinding.FragmentCoinDetailBinding
import com.example.coinrankingapplication.domain.model.CoinModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val viewModel: CoinDetailViewModel by viewModels()
    private var binding: FragmentCoinDetailBinding? = null
    private val args: CoinDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(layoutInflater, container, false)

        viewModel.getFavCoins()
        viewModel.getCoinDetail(args.coinId)
        setupObservers()

        return binding?.root ?: View(context)
    }

    private fun setupObservers() {
        binding?.apply {
            viewModel.state.observe(viewLifecycleOwner) {
                when(it) {
                    is CoinDetailUIState.CoinSuccess -> {
                        loading.isVisible = false
                        tvError.isVisible = false
                        detailLayout.isVisible = true
                        setupDetailScreen(it.coin)
                    }
                    is CoinDetailUIState.Error -> {
                        detailLayout.isVisible = false
                        loading.isVisible = false
                        tvError.isVisible = true
                        tvError.text = it.errorMessage
                    }
                    is CoinDetailUIState.Loading -> {
                        detailLayout.isVisible = !it.isLoading
                        loading.isVisible = it.isLoading
                        tvError.isVisible = !it.isLoading
                    }
                }
            }
        }
    }

    fun setupDetailScreen(item: CoinModel) {
        binding?.apply {
            coinSymbol.text = item.symbol
            currentPriceTitle.text = "Current Price"
            currentPrice.text = item.price
            currentBenefit.text = item.change
            highTitle.text = "High"
            lowTitle.text = "Low"
            highPrice.text = item.highPrice
            lowPrice.text = item.lowPrice
            favCoin.setBackgroundResource(item.isFavourite.isFavMatch())
            if (item.isFavourite) {
                favTitle.text = "Fav"
                favPrice.text = item.favPrice
                favTitle.isVisible = true
                favPrice.isVisible = true

            } else {
                favTitle.isVisible = false
                favPrice.isVisible = false
            }

            val timePeriodList = listOf(
                Constants.H1,
                Constants.H3,
                Constants.H12, H24,
                Constants.D7,
                Constants.D30,
                Constants.M3,
                Constants.Y1,
                Constants.Y3,
                Constants.Y5
            )
            val timePeriodAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, timePeriodList)
            autoCompleteTextView.setText(timePeriodList[3], false)
            autoCompleteTextView.setAdapter(timePeriodAdapter)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                viewModel.getCoinDetail(item.uuid, timePeriodList[i])
                autoCompleteTextView.showDropdown(timePeriodAdapter)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
