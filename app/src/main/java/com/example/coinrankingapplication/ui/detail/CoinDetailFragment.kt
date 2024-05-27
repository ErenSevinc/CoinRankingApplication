package com.example.coinrankingapplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coinrankingapplication.R
import com.example.coinrankingapplication.core.uiState.CoinDetailUIState
import com.example.coinrankingapplication.core.utils.Constants.D30
import com.example.coinrankingapplication.core.utils.Constants.D7
import com.example.coinrankingapplication.core.utils.Constants.H1
import com.example.coinrankingapplication.core.utils.Constants.H12
import com.example.coinrankingapplication.core.utils.Constants.H24
import com.example.coinrankingapplication.core.utils.Constants.H3
import com.example.coinrankingapplication.core.utils.Constants.M3
import com.example.coinrankingapplication.core.utils.Constants.Y1
import com.example.coinrankingapplication.core.utils.Constants.Y3
import com.example.coinrankingapplication.core.utils.Constants.Y5
import com.example.coinrankingapplication.core.utils.isFavMatch
import com.example.coinrankingapplication.core.utils.loadUrl
import com.example.coinrankingapplication.core.utils.setColor
import com.example.coinrankingapplication.core.utils.showDropdown
import com.example.coinrankingapplication.core.utils.toDoublePrice
import com.example.coinrankingapplication.databinding.FragmentCoinDetailBinding
import com.example.coinrankingapplication.domain.model.CoinModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val viewModel: CoinDetailViewModel by viewModels()
    private var binding: FragmentCoinDetailBinding? = null
    private val args: CoinDetailFragmentArgs by navArgs()
    private var selectedTimePeriod = H24


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
                        initDetailScreen(it.coin)
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

    private fun initDetailScreen(item: CoinModel) {
        binding?.apply {
            val timePeriodList = listOf(H1, H3, H12, H24, D7, D30, M3, Y1, Y3, Y5)
            val timePeriodAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, timePeriodList)
            autoCompleteTextView.setText(selectedTimePeriod, false)
            autoCompleteTextView.setAdapter(timePeriodAdapter)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                selectedTimePeriod = timePeriodList[i]
                viewModel.getCoinDetail(item.uuid, timePeriodList[i])
                autoCompleteTextView.showDropdown(timePeriodAdapter)
            }

            coinSymbol.text = item.symbol
            coinName.text = item.name
            currentPrice.text = "$ ${item.price.toDoublePrice()}"
            currentBenefit.setTextColor(ContextCompat.getColor(requireContext(), item.setColor()))
            currentBenefit.text = "% ${item.change}"
            coinImage.loadUrl(item.iconUrl)
            highPrice.text = "$ ${item.highPrice}"
            lowPrice.text = "$ ${item.lowPrice}"
            favTitle.isVisible = item.isFavourite
            favPrice.isVisible = item.isFavourite
            if (item.isFavourite) {
                favPrice.text = "$ ${item.favPrice?.toDoublePrice()}"
            }

            favCoin.setBackgroundResource(item.isFavourite.isFavMatch())
            favCoin.setOnClickListener {
                if (item.isFavourite) {
                    viewModel.deleteCoin(item)
                } else {
                    viewModel.insertCoin(item)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
