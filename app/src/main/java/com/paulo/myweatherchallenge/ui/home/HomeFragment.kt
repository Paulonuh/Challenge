package com.paulo.myweatherchallenge.ui.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.ResolvableApiException
import com.paulo.myweatherchallenge.R
import com.paulo.myweatherchallenge.base.BaseFragment
import com.paulo.myweatherchallenge.databinding.FragmentHomeBinding
import com.paulo.myweatherchallenge.extensions.loadImageCenter
import com.paulo.myweatherchallenge.extensions.navigate
import com.paulo.myweatherchallenge.model.weather.WeatherResponse
import com.paulo.myweatherchallenge.ui.home.adapters.CitiesWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Paulo Henrique Teixeira.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    //region BaseFragment
    override val viewModel: HomeViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    override val spaceIdToTop: Int = R.id.spaceTopHome

    override fun onInitViews() {
        binding.ivCloudsTheme.loadImageCenter(R.drawable.bg_clear_clouds)

        binding.mlHome.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (motionLayout?.progress == 0.0f) {
                    binding.toolbarHome.updateTitle(R.string.clear)
                } else if (motionLayout?.progress == 1.0f) {
                    viewModel.ldWeatherLocation.value?.let {
                        binding.toolbarHome.updateTitle(
                            getString(
                                R.string.city_temp,
                                it.name,
                                it.main?.temp?.toInt().toString()
                            )
                        )
                    }
                }
            }
            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}
        })
    }

    override fun onInitObservers() {
        viewModel.ldWeatherLocation.observe(this) {
            binding.cvMyLocationWeather.isVisible = true
            binding.vHumidity.isVisible = true
            binding.ivLocation.isVisible = true
            setMyLocationInfo(it)
        }
        viewModel.ldWeatherGroup.observe(this) {
            val citiesWeatherAdapter = CitiesWeatherAdapter()
            citiesWeatherAdapter.listener = object : CitiesWeatherAdapter.Listener {
                override fun onCardClicked(data: WeatherResponse) {
                    onDetailClicked(data)
                }
            }
            binding.rvCitiesWeather.adapter = citiesWeatherAdapter

            it.list?.let { list ->
                citiesWeatherAdapter.dataList = list
            }
        }

        viewModel.alertGps.observe(this) { resolvableException ->
            showAlertGps(resolvableException)
        }

        viewModel.locationNotFound.observe(this) {
            binding.cvMyLocationWeather.isVisible = false
        }

        viewModel.ldMyLocationLoading.observe(this) {
            binding.cpiMyLocationWeather.isVisible = it
        }
    }

    override fun onFetchInitialData() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        viewModel.getWeatherGroup()
    }

    override fun showError(message: Int) {}

    override fun onLoading(isLoading: Boolean) {
        binding.cpiHomeList.isVisible = isLoading
    }
    //endregion BaseFragment

    //region Local
    private fun onDetailClicked(data: WeatherResponse) {
        navigate(HomeFragmentDirections.actionHomeFragmentToWeatherDetailFragment(data))
    }

    private fun setMyLocationInfo(data: WeatherResponse) {
        binding.apply {
            tvCity.text = data.name
            tvMain.text = data.weather?.get(0)?.main
            tvTemp.text = context?.getString(R.string.temp, data.main?.temp?.toInt().toString())
            tvFeelsLike.text =
                context?.getString(
                    R.string.temp_feels_like,
                    data.main?.feels_like?.toInt().toString()
                )
            tvMaxAndMin.text = context?.getString(
                R.string.temp_max_min,
                data.main?.temp_max?.toInt().toString(),
                data.main?.temp_max?.toInt().toString()
            )
            tvHumidity.text =
                context?.getString(R.string.humidity, data.main?.humidity.toString())

            //TODO add more icons to represent all possibilities of weather
            when (data.weather?.get(0)?.main) {
                "Clouds" -> ivTemp.loadImageCenter(R.drawable.ic_cloud)
                else -> ivTemp.loadImageCenter(R.drawable.ic_sun)
            }
            cvMyLocationWeather.setOnClickListener { onDetailClicked(data) }
        }
    }

    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                viewModel.fetchLocation()
            } else {
                if (shouldShowRequestPermissionRationale(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                ) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(R.string.review_your_location_permission)
                        .setPositiveButton(R.string.go_to_permissions) { _, _ ->
                            showAndroidSettings()
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ ->
                        }
                        .create()
                        .show()
                }
            }
        }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        permissions.keys.forEach {
            if (it == Manifest.permission.ACCESS_FINE_LOCATION) {
                if (permissions[it] == true) {
                    viewModel.fetchLocation()
                    return@forEach
                }
            } else if (it == Manifest.permission.ACCESS_COARSE_LOCATION) {
                if (permissions[it] == true) {
                    viewModel.fetchLocation()
                }
            }
        }
    }

    private fun showAlertGps(exception: ResolvableApiException) {
        val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
        resolutionForResult.launch(intentSenderRequest)
    }

    private fun showAndroidSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context?.packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        androidSettingResultLauncher.launch(intent)
    }

    private val androidSettingResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    //endregion local
}