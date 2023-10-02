package com.example.orderdeliver.presentation.delivery

import android.graphics.Bitmap
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentChooseAdressBinding
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.presentation.navigation.getBaseScreen
import com.example.orderdeliver.presentation.navigation.getMainNavigator
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.utils.markButtonDisable
import com.example.orderdeliver.utils.showLog
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.RotationType
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ChooseAddressFragment : BaseFragment(R.layout.fragment_choose_adress), CameraListener{

    private val binding: FragmentChooseAdressBinding by viewBinding()
    class Screen : BaseScreen

    override val viewModel: ChooseAddressViewModel by viewModels {
        ChooseAddressViewModel.provideChooseAddressViewModel(factory, getMainNavigator(), getBaseScreen())
    }

    @Inject lateinit var factory: ChooseAddressViewModel.Factory

    private val scaleMapAnimation = Animation(Animation.Type.SMOOTH, 3f)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(requireContext())


        viewModel.requestLocationPermission()
        viewModel.getCurrentLocation()

        observer()

        init()

    }


    private fun observer() {
        viewModel.currentLocation.observe(viewLifecycleOwner) { location ->
            moveCameraToOurPosition(location)
        }
        viewModel.address.observe(viewLifecycleOwner) { result ->

            when(result){
                is SuccessContainer -> {
                    setLoadingStatePendingButton(true)
                    binding.addressEditText.setText(result.data)
                }
                is PendingContainer -> {
                    setLoadingStatePendingButton(false)
                }
                is ErrorContainer -> {

                }
            }
        }
    }

    private fun setLoadingStatePendingButton(isLoaded: Boolean) = with(binding){
        buttonContinue.markButtonDisable(isLoaded)
        progressBar.isVisible = !isLoaded
    }

    private fun init() = with(binding) {
        imageBack.setOnClickListener {
            viewModel.goBack()
        }

        buttonContinue.setOnClickListener {
            viewModel.saveAddress(addressEditText.text.toString())
        }

        addressEditText.setOnClickListener {
            viewModel.manuallyChooseAddress()
        }

        chooseCurrentLocationButton.setOnClickListener {
            viewModel.getCurrentLocation()
        }

        mapView.map.addInputListener(inputListener)
        mapView.map.addCameraListener(this@ChooseAddressFragment)
    }

    private fun moveCameraToOurPosition(location: android.location.Location) {
        binding.mapView.map.move(
            CameraPosition(Point(location.latitude, location.longitude), MAP_ZOOM, MAP_AZIMUTH, MAP_TILT),
            scaleMapAnimation, null
        )
    }


    private val inputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            viewModel.submitSearch(point)
        }

        override fun onMapLongTap(map: Map, p1: Point) {}
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
        if (cameraUpdateReason == CameraUpdateReason.APPLICATION && isFinish) {
            viewModel.submitSearch(cameraPosition.target)
        }else if(cameraUpdateReason == CameraUpdateReason.GESTURES){
            setLoadingStatePendingButton(true)
        }

    }


    private companion object{
        const val MAP_ZOOM = 19.0f
        const val MAP_AZIMUTH = 0.0f
        const val MAP_TILT = 0.0F
    }

}