package com.ranjan.openweather.common

import android.Manifest.permission
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import java.util.*

class GeoLocation(val context: Context) : LocationListener {

    companion object {
        fun getAddressFromLatAndLong(
            context: Context, latitude: Double, longitude: Double
        ): String {
            val geocoder = Geocoder(context, Locale.getDefault())
            @Suppress("DEPRECATION") val addresses =
                geocoder.getFromLocation(latitude, longitude, 1)
            val emptyStr = ""
            return if (addresses?.isNotEmpty() == true) {
                val state = addresses[0]?.adminArea
                val country = addresses[0]?.countryName ?: ""
                "${if (state.isNullOrEmpty()) emptyStr else "$state,"} $country "
            } else ""
        }
    }

    private var locationManager: LocationManager =
        context.getSystemService(LOCATION_SERVICE) as LocationManager

    private lateinit var location: Location

    @RequiresPermission(allOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    fun getLocation(provider: String): Location? {
        try {
            if (locationManager.isProviderEnabled(provider)) {
                locationManager.requestLocationUpdates(
                    provider, 0, 0f, this
                )
                location = locationManager.getLastKnownLocation(provider)!!
                return location
            }
        } catch (e: java.lang.Exception) {
            return null
        }
        return null
    }

    override fun onLocationChanged(location: Location) {
        this.location = location
    }

    fun destroyLocationListener() {
        locationManager.removeUpdates(this)
    }

}