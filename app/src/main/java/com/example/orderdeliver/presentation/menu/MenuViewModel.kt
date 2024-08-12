package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.FoodType
import com.example.orderdeliver.domain.repositories.BasketRepository
import com.example.orderdeliver.domain.repositories.FoodRepository
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.usecases.AddToBasketUseCase
import com.example.orderdeliver.domain.usecases.GetCurrentCityUseCase
import com.example.orderdeliver.domain.usecases.GetPriceForSubjectUseCase
import com.example.orderdeliver.presentation.delivery.PlaceDeliveryFragment
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MenuViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted screen: BaseScreen,
    private val basketRepository: BasketRepository,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val getCurrentCityUseCase: GetCurrentCityUseCase,
    private val getPriceForSubjectUseCase: GetPriceForSubjectUseCase,
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    val foods : Flow<PagingData<FoodDataModel>>

    private val _typeFoods: MutableLiveData<List<TypeFoodModel>> by lazy { MutableLiveData() }
    val typeFoods = _typeFoods.share()

    private val _currentCity: MutableLiveData<String> by lazy { MutableLiveData() }
    val currentCity = _currentCity.share()


    private val _currentFoodType: MutableLiveData<FoodType> by lazy { MutableLiveData(FoodType.ALL) }

    init {
        foods = _currentFoodType.asFlow()
            .debounce(50)
            .flatMapLatest {foodType ->
                foodRepository.getPagedFoods(foodType)
            }
            .cachedIn(viewModelScope)

        _typeFoods.value = foodRepository.getAllFoodTypes()

        viewModelScope.launch {
            getCurrentCityUseCase.listen().collect {
                _currentCity.value = it
            }
        }

    }

    fun setStateTypesById(id: String) {
        val newTypeFoods = foodRepository.setActivatedTypeFoodById(id) ?: return
        _typeFoods.value = newTypeFoods
    }

    fun filterFoods(foodType: FoodType) {
        _currentFoodType.value = foodType
    }

    fun launchToAddBasket(foodDataModel: FoodDataModel) {
        val priceWithDiscount = getPriceForSubjectUseCase(BasketModel(foodDataModel, 1))

        navigator.launch(
            screen = AddToBasketFragment.Screen(foodDataModel.copy(priceWithDiscount = priceWithDiscount)),
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
        filterFoods(_currentFoodType.value ?: FoodType.ALL)
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