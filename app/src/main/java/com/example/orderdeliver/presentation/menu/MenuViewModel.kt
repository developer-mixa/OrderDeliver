package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.orderdeliver.presentation.plugins.core.BaseViewModel
import com.example.orderdeliver.R
import com.example.orderdeliver.data.sources.FoodSource
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.repositories.FoodRepository
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.usecases.AddToBasketUseCase
import com.example.orderdeliver.domain.usecases.GetCurrentCityUseCase
import com.example.orderdeliver.presentation.delivery.PlaceDeliveryFragment
import com.example.orderdeliver.presentation.mappers.FoodToListItemMapper
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.presentation.models.FoodListItem
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import com.example.orderdeliver.presentation.plugins.plugins.ToastPlugin
import com.example.orderdeliver.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val navigator: NavigatorPlugin,
    private val toasts: ToastPlugin,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val getCurrentCityUseCase: GetCurrentCityUseCase,
    private val foodToListItemMapper: FoodToListItemMapper,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    val foods : Flow<PagingData<FoodListItem>>

    private val _typeFoods: MutableLiveData<Container<List<TypeFoodModel>>> by lazy { MutableLiveData() }
    val typeFoods = _typeFoods.share()

    private val _currentCity: MutableLiveData<String> by lazy { MutableLiveData() }
    val currentCity = _currentCity.share()


    private val _currentFoodTypeId: MutableLiveData<String> = MutableLiveData()

    init {
        getTypeFoods()
        listenCurrentCity()

        foods = _currentFoodTypeId.asFlow()
            .debounce(50)
            .flatMapLatest {foodType ->
                foodRepository.getPagedFoods(foodType).map { pagingData ->
                    pagingData.map { foodToListItemMapper.map(it) }
                }
            }
            .cachedIn(viewModelScope)
    }

    fun getTypeFoods() = viewModelScope.launch{
        _typeFoods.value = PendingContainer()

        _typeFoods.value = try {
            SuccessContainer(foodRepository.getAllFoodTypes())
                .also { _currentFoodTypeId.value = FoodSource.ALL_ID }
        } catch (e: Exception){
            ErrorContainer(e)
        }
    }

    private fun listenCurrentCity() = viewModelScope.launch {
        getCurrentCityUseCase.listen().collect {
            _currentCity.value = it
        }
    }


    fun setStateTypesById(id: String) {
        val newTypeFoods = foodRepository.setActivatedTypeFoodById(id) ?: return
        _typeFoods.value = SuccessContainer(newTypeFoods)
    }

    fun filterFoods(foodType: String = FoodSource.ALL_ID) {
        _currentFoodTypeId.value = foodType
    }

    fun launchToAddBasket(foodDataModel: FoodDataModel) {
        navigator.launch(
            screen = AddToBasketFragment.Screen(foodDataModel),
            addToBackStack = true,
            aboveAll = true
        )
    }

    fun addBasket(foodDataModel: FoodDataModel) = viewModelScope.launch {
        try {
            addToBasketUseCase.invoke(foodDataModel)
        } catch (_: ReachedLimitException) {
            toasts.toast(R.string.reached_limit_text)
        }

    }

    fun launchToPlaceDelivery() {
        navigator.launch(
            screen = PlaceDeliveryFragment.Screen(),
            addToBackStack = true,
            aboveAll = true
        )
    }

}