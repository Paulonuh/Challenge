package com.paulo.myweatherchallenge.helpers.gps

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.*
import com.paulo.myweatherchallenge.model.exception.LocationNotFoundException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Created by Paulo Henrique Teixeira.
 */

class GpsHelper @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        const val REQUEST_ENABLE_GPS = 987
        private const val REQUEST_LOCATION_TIME_OUT = 10_000
    }

    private val handler: Handler by lazy { Handler(Looper.myLooper()!!) }

    private var isIdle = true
    private var isGpsActivated = false
    private var lastLocation: Location? = null
    private var locationManager: LocationManager? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var onLoadingChangeListener: ((isLoading: Boolean) -> Unit)? = null
    private var onErrorListener: ((exception: Exception) -> Unit)? = null
    private var onSuccessListener: ((location: Location) -> Unit)? = null

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10_000
            fastestInterval = 5_000
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.let {
                for (location in locationResult.locations) {
                    lastLocation = location
                    finishRequestLocation()
                    break
                }
            }
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)
        }
    }

    fun fetchLastLocation(onLoadingChangeListener: (isLoading: Boolean) -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
        onSuccessListener: (location: Location) -> Unit
    ) {
        this.onLoadingChangeListener = onLoadingChangeListener
        this.onErrorListener = onErrorListener
        this.onSuccessListener = onSuccessListener

        startFetching()
    }

    private fun startFetching() {
        onLoadingChangeListener?.invoke(true)

        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }

        if (locationManager == null) {
            locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        if (isIdle) {
            displayLocationSettingsRequest(context)
        }
    }

    @SuppressLint("MissingPermission")
    private fun displayLocationSettingsRequest(
        context: Context
    ) {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())
        task.addOnCompleteListener { responseTask ->

            if (responseTask.exception != null) {
                if (responseTask.exception is ResolvableApiException) {
                    // Location settings are not satisfied
                    val resolvable = responseTask.exception as ResolvableApiException

                    try {
                        onLoadingChangeListener?.invoke(false)
                        onErrorListener?.invoke(resolvable)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                } else {
                    onLoadingChangeListener?.invoke(false)
                    onErrorListener?.invoke(Exception())
                }

                isGpsActivated = false
            } else {
                isIdle = false
                isGpsActivated = true
                lastLocation = null

                fusedLocationClient?.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )

                increaseToTimeOut(1_000)
            }
        }
    }

    private fun increaseToTimeOut(amount: Int) {
        handler.postDelayed({
            try {
                if (amount == REQUEST_LOCATION_TIME_OUT) {
                    finishRequestLocation()
                } else if (onSuccessListener != null) {
                    increaseToTimeOut(amount + 1_000)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }, 1_000)
    }

    private fun finishRequestLocation() {
        isIdle = true
        fusedLocationClient?.removeLocationUpdates(locationCallback)

        val lastLocation = this.lastLocation

        if (lastLocation == null) {
            onLoadingChangeListener?.invoke(false)
            onErrorListener?.invoke(LocationNotFoundException())
        } else if (onSuccessListener != null) {
            onLoadingChangeListener?.invoke(false)
            onSuccessListener?.invoke(lastLocation)
            onSuccessListener = null
        }
    }
}