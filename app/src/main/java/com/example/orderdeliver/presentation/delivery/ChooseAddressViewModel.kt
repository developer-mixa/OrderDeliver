package com.example.orderdeliver.presentation.delivery

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.orderdeliver.presentation.plugins.core.BaseViewModel
import com.example.orderdeliver.presentation.plugins.core.Event
import com.example.orderdeliver.domain.repositories.AddressRepository
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.helpers.YandexMapHelper.convertAddressToPoint
import com.example.orderdeliver.presentation.delivery.models.CityModel
import com.example.orderdeliver.presentation.plugins.plugins.LocationPlugin
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import com.example.orderdeliver.presentation.plugins.plugins.PermissionPlugin
import com.example.orderdeliver.presentation.plugins.plugins.permissions.Permissions
import com.example.orderdeliver.utils.share
import com.example.orderdeliver.utils.showLog
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseAddressViewModel @Inject constructor(
    private val navigator: NavigatorPlugin,
    private val locationPlugin: LocationPlugin,
    private val permissionPlugin: PermissionPlugin,
    private val addressRepository: AddressRepository,
) : BaseViewModel() {

    private val _currentLocation: MutableLiveData<Point> = MutableLiveData()
    val currentLocation = _currentLocation.share()

    private val _address: MutableLiveData<Container<String>> = MutableLiveData()
    val address = _address.share()

    private var searchManager: SearchManager =
        SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)

    private val searchListener = object : Session.SearchListener {
        override fun onSearchResponse(response: Response) {
            val street = getNameByKindComponent(response, Address.Component.Kind.STREET)
            val house = getNameByKindComponent(response, Address.Component.Kind.HOUSE)
            val city = getNameByKindComponent(response, Address.Component.Kind.LOCALITY)

            val doneAddress = "$city, $street, $house"

            _address.value = SuccessContainer(doneAddress)
        }

        override fun onSearchError(exception: Error) {
            _address.value = ErrorContainer(Exception(exception.toString()))
        }
    }


    fun goBack() = navigator.goBack()

    fun saveAddress(address: String) = viewModelScope.launch {
        addressRepository.writeAddress(address)
    }

    init {
        _address.value = PendingContainer()
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is Event<*>) {
            val rs = result.getValue() ?: return

            if (rs is CityModel) {
                searchManager.resolveURI(rs.title, SearchOptions(), searchListener)
                convertAddressToPoint(searchManager, rs.title, blockSuccess = { point ->
                    if (point != null) {
                        _currentLocation.value = point!!
                    }
                }, blockError = {
                    showLog(it)
                }
                )
            }
        }
    }


    fun submitSearch(point: Point) {
        _address.value = PendingContainer()
        searchManager.submit(
            point,
            20,
            SearchOptions(),
            searchListener
        )
    }


    fun manuallyChooseAddress() {
        navigator.launch(ManualChooseFragment.Screen(), addToBackStack = true, aboveAll = true)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        _address.value = PendingContainer()
        locationPlugin.getLastLocation { _currentLocation.value = it }
    }

    // TODO (Убрать android зависимости из ViewModel0)

    fun requestLocationPermission() {
        permissionPlugin.requestPermissions(
            Permissions.ACCESS_FINE_LOCATION,
            Permissions.ACCESS_COARSE_LOCATION
        )
    }

    private fun getNameByKindComponent(
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

}