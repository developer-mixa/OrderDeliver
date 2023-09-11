package com.example.orderdeliver.presentation.delivery

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentChooseAdressBinding
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.utils.showLog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.Address.Component.Kind
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import java.nio.file.Files.move


class ChooseAddressFragment : Fragment(R.layout.fragment_choose_adress), CameraListener {

    private val binding: FragmentChooseAdressBinding by viewBinding()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var searchManager: SearchManager
    private lateinit var searchSession: Session

    class Screen: BaseScreen


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(requireContext())
        requestLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLastKnownLocation()
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)
        binding.mapView.map.addInputListener(inputListener)
        binding.mapView.map.addCameraListener(this)
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    binding.mapView.map.move(CameraPosition(Point(location.latitude, location.longitude), 11.0f, 0.0f,0.0f),
                        Animation(Animation.Type.SMOOTH, 2f), null)
                }

            }
    }

    private val searchListener = object : Session.SearchListener {
        override fun onSearchResponse(response: Response) {
            val street = getNameKindComponent(response, Kind.STREET)
            val house = getNameKindComponent(response, Kind.HOUSE)
            val city = getNameKindComponent(response, Kind.LOCALITY)

            val doneText = "$city, $street, $house"

            binding.addressEditText.setText(doneText)
        }

        override fun onSearchError(p0: com.yandex.runtime.Error) {}
    }

    private fun getNameKindComponent(response: Response, kind: Kind, messageIfNotFound: String = "-"): String{
        return response.collection.children.firstOrNull()?.obj
            ?.metadataContainer
            ?.getItem(ToponymObjectMetadata::class.java)
            ?.address
            ?.components
            ?.firstOrNull { it.kinds.contains(kind)}
            ?.name ?: messageIfNotFound
    }

    private val inputListener = object : InputListener{
        override fun onMapTap(map: Map, point: Point) {
            searchSession = searchManager.submit(point, 20, SearchOptions(), searchListener)

        }

        override fun onMapLongTap(map: Map, p1: Point) {

        }

    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        }
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        isFinish: Boolean,
    ) {
        if (cameraUpdateReason == CameraUpdateReason.APPLICATION && isFinish){
            searchSession = searchManager.submit(cameraPosition.target, 20, SearchOptions(), searchListener)
        }
    }

}