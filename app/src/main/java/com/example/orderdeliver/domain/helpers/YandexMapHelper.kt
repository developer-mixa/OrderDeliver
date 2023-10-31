package com.example.orderdeliver.domain.helpers

import android.annotation.SuppressLint
import android.content.Context
import com.example.orderdeliver.presentation.delivery.ManualChooseViewModel
import com.example.orderdeliver.utils.showLog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.Error

object YandexMapHelper {

    @SuppressLint("StaticFieldLeak")
    private var fusedLocationClient: FusedLocationProviderClient? = null
    fun getNameKindComponent(
        response: Response,
        kind: Address.Component.Kind,
        messageIfNotFound: String = "-",
    ): String {
        return response.collection.children.firstOrNull()?.obj
            ?.metadataContainer
            ?.getItem(ToponymObjectMetadata::class.java)
            ?.address
            ?.components
            ?.firstOrNull { it.kinds.contains(kind) }
            ?.name ?: messageIfNotFound
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context, successBlock: (Point) -> Unit) {
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    successBlock(Point(location.latitude, location.longitude))
                }
            }
    }

    fun convertAddressToPoint(searchManager: SearchManager, address: String, blockSuccess: (Point?) -> Unit, blockError: ((String) -> Unit)? = null) {
        showLog("start working")
        searchManager.submit(
            address,
            Geometry.fromBoundingBox(ManualChooseViewModel.BOUNDING_BOX),
            SearchOptions(),
            object : Session.SearchListener {
                override fun onSearchResponse(response: Response) {
                    val obj = response.collection.children[0].obj
                    if (obj == null){
                        if (blockError == null) return
                        blockError("Objects aren't found, object == null")
                    }else {
                        blockSuccess(obj.geometry[0].point)
                    }

                }

                override fun onSearchError(error: Error) {
                    if (blockError == null) return
                    blockError("Search isn't success, error valid: ${error.isValid}")
                }

            })
    }

}