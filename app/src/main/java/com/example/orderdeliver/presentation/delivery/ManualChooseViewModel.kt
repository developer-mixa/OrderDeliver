package com.example.orderdeliver.presentation.delivery

import androidx.lifecycle.viewModelScope
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.exceptions.SuggestException
import com.example.orderdeliver.presentation.delivery.models.CityModel
import com.example.orderdeliver.utils.showLog
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.SuggestItem
import com.yandex.mapkit.search.SuggestOptions
import com.yandex.mapkit.search.SuggestSession
import com.yandex.mapkit.search.SuggestSession.SuggestListener
import com.yandex.mapkit.search.SuggestType
import com.yandex.runtime.Error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


class ManualChooseViewModel(
    private val navigator: Navigator,
    val screen: ManualChooseFragment.Screen,
) : BaseViewModel() {

    private val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
    private var suggestSession: SuggestSession? = null

    private val _suggests: MutableStateFlow<Container<List<CityModel>>> = MutableStateFlow(PendingContainer())
    val suggests = _suggests.asStateFlow().debounce(100)

    fun submitSearchCity(query: String){
        emitSuggests(PendingContainer())
        if (suggestSession == null)
            suggestSession = searchManager.createSuggestSession()

        suggestSession!!.suggest(query, BOUNDING_BOX, SuggestOptions().setSuggestTypes(SuggestType.GEO.value), suggestListener)
    }

    private val suggestListener = object : SuggestListener{
        override fun onResponse(suggests: MutableList<SuggestItem>) {
            viewModelScope.launch {
                val cities = suggests.map {
                    CityModel(it.uri ?: "", it.displayText ?: "", it.displayText ?: "")
                }
                emitSuggests(SuccessContainer(cities))
            }
        }

        override fun onError(error: Error) {
            viewModelScope.launch {
                emitSuggests(ErrorContainer(SuggestException()))
            }
        }

    }


    fun goBack(cityModel: CityModel? = null){
        navigator.goBack(cityModel)
    }

    private fun emitSuggests(suggests: Container<List<CityModel>>) = viewModelScope.launch {
        _suggests.emit(suggests)
    }

    companion object{
        val BOUNDING_BOX = BoundingBox(
            Point(66.066191, 76.468435),
            Point(66.149326, 76.824836)
        )
    }

}