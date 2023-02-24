package com.ranjan.openweather.view.dashboard.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ranjan.openweather.common.GeoLocation
import com.ranjan.openweather.common.ResponseHelper
import com.ranjan.openweather.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val geoLocation by lazy { GeoLocation(requireActivity()) }


    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        getLocation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.weatherResponse.flowWithLifecycle(
                viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED
            ).collect {
                binding.vs.displayedChild = 0
                binding.progressBar.visibility = View.GONE
                binding.noInternet.visibility = View.GONE
                when (it) {
                    is ResponseHelper.Error -> {
                        if (it.message == "No Internet Connection") binding.noInternet.visibility =
                            View.VISIBLE
                        binding.errorText.text = it.message
                    }
                    is ResponseHelper.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResponseHelper.Success -> {
                        binding.vs.displayedChild = 1
                        it.data?.let { res ->
                            binding.weather = res
                            binding.tvLocation.text = GeoLocation.getAddressFromLatAndLong(
                                view.context, res.coordinate.latitude, res.coordinate.longitude
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        val location: Location? = geoLocation.getLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val latitude: Double = location.latitude
            val longitude: Double = location.longitude
            geoLocation.destroyLocationListener()
            if (viewModel.weatherResponse.value.data == null)
                viewModel.getWeatherData(latitude, longitude)
        }
    }


    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) getLocation()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}