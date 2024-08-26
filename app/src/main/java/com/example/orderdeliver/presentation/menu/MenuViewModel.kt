package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.data.sources.FoodSource
import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.repositories.FoodRepository
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.usecases.AddToBasketUseCase
import com.example.orderdeliver.domain.usecases.GetCurrentCityUseCase
import com.example.orderdeliver.domain.usecases.GetPriceForSubjectUseCase
import com.example.orderdeliver.presentation.delivery.PlaceDeliveryFragment
import com.example.orderdeliver.presentation.mappers.FoodToListItemMapper
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.presentation.models.FoodListItem
import com.example.orderdeliver.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MenuViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted screen: BaseScreen,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val getCurrentCityUseCase: GetCurrentCityUseCase,
    private val getPriceForSubjectUseCase: GetPriceForSubjectUseCase,
    private val foodToListItemMapper: FoodToListItemMapper,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    val foods : Flow<PagingData<FoodListItem>>

    private val _typeFoods: MutableLiveData<List<TypeFoodModel>> by lazy { MutableLiveData() }
    val typeFoods = _typeFoods.share()

    private val _currentCity: MutableLiveData<String> by lazy { MutableLiveData() }
    val currentCity = _currentCity.share()


    private val _currentFoodTypeId: MutableLiveData<String> by lazy { MutableLiveData(FoodSource.ALL_ID) }

    init {
        foods = _currentFoodTypeId.asFlow()
            .debounce(50)
            .flatMapLatest {foodType ->
                foodRepository.getPagedFoods(foodType)
            }
            .map { pagingData -> pagingData.map { foodToListItemMapper.map(it) } }
            .cachedIn(viewModelScope)

        viewModelScope.launch {
            _typeFoods.value = foodRepository.getAllFoodTypes()

            getCurrentCityUseCase.listen().collect {
                _currentCity.value = it
            }
        }

    }

    fun setStateTypesById(id: String) {
        val newTypeFoods = foodRepository.setActivatedTypeFoodById(id) ?: return
        _typeFoods.value = newTypeFoods
    }

    fun filterFoods(foodType: String) {
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
            navigator.toast(R.string.reached_limit_text)
        }

    }

    fun retry(){
        filterFoods(_currentFoodTypeId.value ?: FoodSource.ALL_ID)
    }

    fun launchToPlaceDelivery() {
        navigator.launch(
            screen = PlaceDeliveryFragment.Screen(),
            addToBackStack = true,
            aboveAll = true
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator, screen: BaseScreen): MenuViewModel
    }

    companion object {
        fun provideMenuViewModelFactory(
            factory: Factory,
            navigator: Navigator,
            screen: BaseScreen
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(navigator, screen) as T
                }
            }
        }
    }

}