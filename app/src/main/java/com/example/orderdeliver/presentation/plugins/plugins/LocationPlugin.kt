package com.example.orderdeliver.presentation.plugins.plugins

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.geometry.Point
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationPlugin @Inject constructor() : ActivityPlugin() {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onResume(activity: AppCompatActivity) {
        super.onResume(activity)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(onSuccess: (Point) -> Unit){
        if (fusedLocationClient == null) return

        fusedLocationClient!!.lastLocation
            .addOnSuccessListener { location ->
                onSuccess(Point(location.latitude, location.longitude))
            }
    }

}