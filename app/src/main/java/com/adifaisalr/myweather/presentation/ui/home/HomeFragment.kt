package com.adifaisalr.myweather.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.adifaisalr.myweather.R
import com.adifaisalr.myweather.data.api.Api
import com.adifaisalr.myweather.databinding.FragmentHomeBinding
import com.adifaisalr.myweather.domain.model.CurrentWeather
import com.adifaisalr.myweather.domain.model.DataHolder
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val args: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.lat = args.lat.toDouble()
        viewModel.lon = args.lon.toDouble()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.getCurrentWeather()
    }

    private fun observeViewModel() {
        viewModel.currentWeatherResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataHolder.Success -> {
                    setLoading(false)
                    result.data.let { currentWeather ->
                        setWeatherView(currentWeather)
                    }
                }
                is DataHolder.Loading -> {
                    setLoading(true)
                    setError(null)
                }
                is DataHolder.Failure -> {
                    setLoading(false)
                    setError(result.errorData.message)
                }
                else -> {
                    setLoading(false)
                    setError(getString(R.string.general_error))
                }
            }
        }
    }

    private fun setWeatherView(currentWeather: CurrentWeather) {
        val weather = currentWeather.weather.firstOrNull()
        binding.textHome.text = getString(R.string.todays_weather, currentWeather.name)
        binding.currentWeatherTitleTV.text = weather?.main
        binding.currentWeatherDescTV.text = weather?.description
        val imgUrl = Api.DEFAULT_BASE_IMAGE_URL + weather?.icon + Api.IMAGE_URL_POSTFIX
        Glide.with(binding.currentWeatherIV)
            .load(imgUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.currentWeatherIV)
        binding.currentWeatherTempTV.text =
            getString(R.string.temp_range, currentWeather.main.tempMin, currentWeather.main.tempMax)
        binding.currentWeatherHumidityTV.text = getString(R.string.humidity, currentWeather.main.humidity)
        binding.currentWeatherWindTV.text = getString(R.string.wind_speed, currentWeather.wind.speed)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loading.isVisible = isLoading
        binding.weatherContentLayout.isVisible = !isLoading
    }

    private fun setError(errorMessage: String?) {
        binding.weatherContentLayout.isVisible = errorMessage.isNullOrEmpty()
        binding.error.isVisible = !errorMessage.isNullOrEmpty()
        binding.error.text = errorMessage
    }
}