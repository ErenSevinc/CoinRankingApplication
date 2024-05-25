package com.example.coinrankingapplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coinrankingapplication.databinding.FragmentCoinDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val viewModel: CoinDetailViewModel by viewModels()
    private var binding: FragmentCoinDetailBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root ?: View(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
