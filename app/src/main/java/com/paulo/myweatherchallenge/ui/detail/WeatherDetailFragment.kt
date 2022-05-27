package com.paulo.myweatherchallenge.ui.detail

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.paulo.myweatherchallenge.R
import com.paulo.myweatherchallenge.base.BaseFragment
import com.paulo.myweatherchallenge.databinding.FragmentWeatherDetailBinding
import com.paulo.myweatherchallenge.extensions.loadImageCenter
import com.paulo.myweatherchallenge.extensions.popBackStack
import com.paulo.myweatherchallenge.model.weather.WeatherDetail
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Paulo Henrique Teixeira.
 */

@AndroidEntryPoint
class WeatherDetailFragment : BaseFragment<FragmentWeatherDetailBinding>() {

    //region BaseFragment
    override val viewModel: WeatherDetailViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> FragmentWeatherDetailBinding
        get() = FragmentWeatherDetailBinding::inflate

    override val spaceIdToTop: Int? = null
    private val navArgs: WeatherDetailFragmentArgs by navArgs()

    override fun onInitViews() {
        binding.ibBack.setOnClickListener { popBackStack() }
        navArgs.weatherData.name?.let { binding.toolbarHome.updateTitle(it) }

        binding.apply {
            tvCity.text = navArgs.weatherData.name
            tvMain.text = navArgs.weatherData.weather?.get(0)?.main
            tvTemp.text = context?.getString(R.string.temp, navArgs.weatherData.main?.temp?.toInt().toString())
            tvFeelsLike.text =
                context?.getString(
                    R.string.temp_feels_like,
                    navArgs.weatherData.main?.feels_like?.toInt().toString()
                )
            tvMaxAndMin.text = context?.getString(
                R.string.temp_max_min,
                navArgs.weatherData.main?.temp_max?.toInt().toString(),
                navArgs.weatherData.main?.temp_max?.toInt().toString()
            )
            tvHumidity.text =
                context?.getString(R.string.humidity, navArgs.weatherData.main?.humidity.toString())

            //TODO add more icons to represent all possibilities of weather
            when (navArgs.weatherData.weather?.get(0)?.main) {
                "Clouds" -> ivTemp.loadImageCenter(R.drawable.ic_cloud)
                else -> ivTemp.loadImageCenter(R.drawable.ic_sun)
            }
        }
    }

    override fun onInitObservers() {
        viewModel.ldDetail.observe(this) { detail ->
            configDetail(detail)
        }
    }

    private fun configDetail(detail: WeatherDetail?) {
        binding.apply {
            clDetail.isVisible = true
            //todo convert to time based on timezone
            tvSunriseValue.text = detail?.current?.sunrise.toString()
            //todo convert to time based on timezone
            tvSunsetValue.text = detail?.current?.sunset.toString()
            tvWindValue.text = detail?.current?.wind_speed.toString()
            tvUvValue.text = detail?.current?.uvi.toString()
        }
    }

    override fun onFetchInitialData() {
        viewModel.fetchDetail(navArgs.weatherData.coord?.lat, navArgs.weatherData.coord?.lon)
    }

    override fun showError(message: Int) {
    }

    override fun onLoading(isLoading: Boolean) {
        binding.cpi.isVisible = isLoading
    }
    //endregion BaseFragment

}