package com.example.coinrankingapplication.ui.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinrankingapplication.R
import com.example.coinrankingapplication.core.Constants.D30
import com.example.coinrankingapplication.core.Constants.D7
import com.example.coinrankingapplication.core.Constants.H1
import com.example.coinrankingapplication.core.Constants.H12
import com.example.coinrankingapplication.core.Constants.H24
import com.example.coinrankingapplication.core.Constants.H3
import com.example.coinrankingapplication.core.Constants.M3
import com.example.coinrankingapplication.core.Constants.Y1
import com.example.coinrankingapplication.core.Constants.Y3
import com.example.coinrankingapplication.core.Constants.Y5
import com.example.coinrankingapplication.core.showDropdown
import com.example.coinrankingapplication.databinding.FragmentCoinListBinding
import com.example.coinrankingapplication.ui.list.adapter.CoinsAdapter
import com.example.coinrankingapplication.ui.list.adapter.LoaderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private val viewModel: CoinListViewModel by viewModels()
    private var binding: FragmentCoinListBinding? = null
    private var adapter: CoinsAdapter? = null
    private var selectedTimePeriod = H24


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinListBinding.inflate(layoutInflater, container, false)

        initLayout()
        setupObservers()

        return binding?.root ?: View(context)
    }

    private fun initLayout() {
        adapter = CoinsAdapter()
        binding?.apply {
            val timePeriodList = listOf(H1, H3, H12, H24, D7, D30, M3, Y1, Y3, Y5)
            val timePeriodAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, timePeriodList)
            autoCompleteTextView.setText(timePeriodList[3], false)
            autoCompleteTextView.setAdapter(timePeriodAdapter)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                selectedTimePeriod = timePeriodList[i]
                lifecycleScope.launch {
                    viewModel.getCoinList(timePeriodList[i])
                }
                autoCompleteTextView.showDropdown(timePeriodAdapter)
            }
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listCoin.layoutManager = layoutManager

            listCoin.adapter = adapter?.withLoadStateHeaderAndFooter(
                header = LoaderAdapter {
                    adapter?.retry()
                },
                footer = LoaderAdapter {
                    adapter?.retry()
                }
            )
            adapter?.addLoadStateListener {loadState ->
                listCoin.isVisible = loadState.source.refresh is LoadState.NotLoading
                loading.isVisible = loadState.source.refresh is LoadState.Loading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error

                handleError(loadState)
            }

            buttonRetry.setOnClickListener {
                adapter?.retry()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.getCoinList(selectedTimePeriod).collectLatest { coins ->
                adapter?.submitData(coins)
            }
        }
    }

    private fun handleError(loadStates: CombinedLoadStates) {
        val errorState = loadStates.source.append as? LoadState.Error
            ?: loadStates.source.prepend as? LoadState.Error


        errorState?.let {
            Toast.makeText(requireContext(),errorState.error.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }
}