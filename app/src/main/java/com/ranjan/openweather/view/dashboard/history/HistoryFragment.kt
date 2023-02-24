package com.ranjan.openweather.view.dashboard.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranjan.openweather.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding get() = _binding!!

    private lateinit var weatherAdapter: WeatherHistoryAdapter
    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherAdapter = WeatherHistoryAdapter()
        binding.rv.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        }
        lifecycleScope.launch {
            viewModel.getPreviousWeatherData.flowWithLifecycle(
                viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED
            ).collect {
                weatherAdapter.submitList(it.reversed())
            }
        }
    }

    override fun onDestroyView() {
        binding.rv.adapter = null
        _binding = null
        super.onDestroyView()
    }
}