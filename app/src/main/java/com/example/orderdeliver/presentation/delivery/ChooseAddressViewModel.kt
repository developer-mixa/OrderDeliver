package com.example.orderdeliver.presentation.delivery

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.AddressRepository
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.utils.share
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.image.ImageProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.lang.Exception

//Taks: progress bar on button || when onPressed data about map changes

class ChooseAddressViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted screen: BaseScreen,
    private val addressRepository: AddressRepository
) : BaseViewModel() {

    private val _currentLocation: MutableLiveData<android.location.Location> = MutableLiveData()
    val currentLocation = _currentLocation.share()

    private val _address: MutableLiveData<Container<String>> = MutableLiveData()
    val address = _address.share()

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var searchManager: SearchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)





    private val searchListener = object : Session.SearchListener {
        override fun onSearchResponse(response: Response) {
            val street = getNameKindComponent(response, Address.Component.Kind.STREET)
            val house = getNameKindComponent(response, Address.Component.Kind.HOUSE)
            val city = getNameKindComponent(response, Address.Component.Kind.LOCALITY)

            val doneAddress = "$city, $street, $house"

            _address.value = SuccessContainer(doneAddress)
        }

        override fun onSearchError(exception: com.yandex.runtime.Error) {
            _address.value = ErrorContainer(Exception())
        }
    }

    fun goBack() = navigator.goBack()

    fun saveAddress(address: String) = viewModelScope.launch{
        addressRepository.writeAddress(address)
    }

    init {
        _address.value = PendingContainer()
    }

    fun submitSearch(point: Point){
        _address.value = PendingContainer()
        searchManager.submit(
            point,
            20,
            SearchOptions(),
            searchListener
        )
    }

    fun manuallyChooseAddress(){
        navigator.launch(ManualChooseFragment.Screen(),addToBackStack = true, aboveAll = true)
    }

    @SuppressLint("MissingPermission")
     fun getCurrentLocation() = navigator.activityScope {
        _address.value = PendingContainer()
        if (fusedLocationClient == null) fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(it)

        fusedLocationClient!!.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    _currentLocation.value = location
                }
            }
    }

    fun requestLocationPermission() = navigator.activityScope {
        if (ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
        }
    }

    private fun getNameKindComponent(
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


    @AssistedFactory
    interface Factory{
        fun create(navigator: Navigator, screen: BaseScreen) : ChooseAddressViewModel
    }

    companion object{
        fun provideChooseAddressViewModel(factory: ChooseAddressViewModel.Factory, navigator: Navigator, screen: BaseScreen) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(navigator, screen) as T
                }
            }
        }
    }

}