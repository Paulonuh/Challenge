package com.paulo.myweatherchallenge.ui.home

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.paulo.myweatherchallenge.base.BaseFragment
import com.paulo.myweatherchallenge.databinding.FragmentHomeBinding
import com.paulo.myweatherchallenge.extensions.navigate
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Paulo Henrique Teixeira.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onInitViews() {

        binding.buttonFirst.setOnClickListener {
            navigate(HomeFragmentDirections.actionHomeFragmentToWeatherDetailFragment())
        }
    }

    override fun onInitObservers() {
        viewModel.ldWeather.observe(this){
            val a = it
        }
    }

    override fun onFetchInitialData() {
        viewModel.getWeather()
    }

    override fun showError(message: Int) {
    }

    override fun onLoading(isLoading: Boolean) {
    }

}