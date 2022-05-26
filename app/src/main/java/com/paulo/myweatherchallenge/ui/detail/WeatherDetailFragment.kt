package com.paulo.myweatherchallenge.ui.detail

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.paulo.myweatherchallenge.base.BaseFragment
import com.paulo.myweatherchallenge.base.BaseViewModel
import com.paulo.myweatherchallenge.databinding.FragmentWeatherDetailBinding
import com.paulo.myweatherchallenge.extensions.popBackStack
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Paulo Henrique Teixeira.
 */

@AndroidEntryPoint
class WeatherDetailFragment : BaseFragment<FragmentWeatherDetailBinding>() {
    override val viewModel: WeatherDetailViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> FragmentWeatherDetailBinding
        get() = FragmentWeatherDetailBinding::inflate

    override val spaceIdToTop: Int? = null

    override fun onInitViews() {
        binding.buttonSecond.setOnClickListener { popBackStack() }
    }

    override fun onInitObservers() {
    }

    override fun onFetchInitialData() {
    }

    override fun showError(message: Int) {
    }

    override fun onLoading(isLoading: Boolean) {
    }
}