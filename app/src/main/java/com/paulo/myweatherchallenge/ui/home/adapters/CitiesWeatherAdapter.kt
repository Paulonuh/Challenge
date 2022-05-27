package com.paulo.myweatherchallenge.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.paulo.myweatherchallenge.R
import com.paulo.myweatherchallenge.base.BaseAdapter
import com.paulo.myweatherchallenge.base.ViewHolder
import com.paulo.myweatherchallenge.databinding.AdapterCitiesWeatherBinding
import com.paulo.myweatherchallenge.extensions.loadImageCenter
import com.paulo.myweatherchallenge.model.weather.WeatherResponse


/**
 * Created by Paulo Henrique Teixeira.
 */

class CitiesWeatherAdapter : BaseAdapter<AdapterCitiesWeatherBinding, WeatherResponse>() {

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterCitiesWeatherBinding
        get() = { layoutInflater, viewGroup -> AdapterCitiesWeatherBinding.inflate(layoutInflater, viewGroup, false) }

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterCitiesWeatherBinding>,
        data: WeatherResponse,
        position: Int
    ) {
        holder.binding.apply {
            tvCity.text = data.name
            tvMain.text = data.weather?.get(0)?.main
            tvTemp.text = holder.binding.tvTemp.context.getString(R.string.temp, data.main?.temp?.toInt().toString())
            tvFeelsLike.text =
                holder.binding.tvFeelsLike.context.getString(
                    R.string.temp_feels_like,
                    data.main?.feels_like?.toInt().toString()
                )
            tvMaxAndMin.text = holder.binding.tvMaxAndMin.context.getString(
                R.string.temp_max_min,
                data.main?.temp_max?.toInt().toString(),
                data.main?.temp_min?.toInt().toString()
            )
            tvHumidity.text =
                holder.binding.tvHumidity.context.getString(R.string.humidity, data.main?.humidity.toString())

            //TODO add more icons to represent more possibilities of weather
            when (data.weather?.get(0)?.main) {
                "Clouds" -> ivTemp.loadImageCenter(R.drawable.ic_cloud)
                else -> ivTemp.loadImageCenter(R.drawable.ic_sun)
            }

            space.isVisible = itemCount == position + 1

            cvCity.setOnClickListener { listener?.onCardClicked(data) }
        }
    }

    var listener: Listener? = null

    interface Listener {
        fun onCardClicked(data: WeatherResponse)
    }
}