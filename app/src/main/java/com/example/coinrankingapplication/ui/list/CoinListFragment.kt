package com.example.coinrankingapplication.ui.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrankingapplication.R
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
import com.example.coinrankingapplication.core.utils.showDropdown
import com.example.coinrankingapplication.databinding.FragmentCoinListBinding
import com.example.coinrankingapplication.ui.list.adapter.CoinModelAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private val viewModel: CoinListViewModel by viewModels()
    private var binding: FragmentCoinListBinding? = null
    private var adapter: CoinModelAdapter? = null
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
        adapter = CoinModelAdapter(
            onClick = {
            val directions = CoinListFragmentDirections.navigateToDetail(it)
            findNavController().navigate(directions)
            },
            onFavClick = {
                if (!it.isFavourite) {
                    viewModel.insertCoin(it)
                } else {
                    viewModel.deleteCoin(it)
                }
            }
        )
        binding?.apply {
            val timePeriodList = listOf(H1, H3, H12, H24, D7, D30, M3, Y1, Y3, Y5)
            val timePeriodAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, timePeriodList)
            autoCompleteTextView.setText(timePeriodList[3], false)
            autoCompleteTextView.setAdapter(timePeriodAdapter)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                selectedTimePeriod = timePeriodList[i]
                viewModel.refreshData(timePeriodList[i])
                autoCompleteTextView.showDropdown(timePeriodAdapter)
            }
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listCoin.layoutManager = layoutManager
            listCoin.adapter = adapter

            listCoin.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.fetchData()
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.coins.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
            Log.e("LIST", it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }
}