package com.example.orderdeliver.presentation.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigation.BaseScreen
import com.example.navigation.Navigator
import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.domain.usecases.TapToMenuUseCase
import com.example.orderdeliver.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class BasketViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted val screen: BaseScreen,
    private val defaultBasketRepository: DefaultBasketRepository,
    private val tapToMenuUseCase: TapToMenuUseCase
): ViewModel() {

    private val _baskets: MutableLiveData<List<BasketModel>?> = MutableLiveData<List<BasketModel>?>()
    val baskets = _baskets.share()

    init {
        _baskets.value = defaultBasketRepository.getBaskets()
    }

    fun toMainMenu(){
        viewModelScope.launch {
            tapToMenuUseCase.toMain()
        }
    }

    fun getPriceForAll(): Int{
        if (_baskets.value == null) return 0

        return defaultBasketRepository.priceForAll(_baskets.value!!)
    }

    fun addCountItem(foodDataModel: FoodDataModel){
        defaultBasketRepository.addBasket(foodDataModel)
        _baskets.value = defaultBasketRepository.getBaskets()

    }

    fun removeCountItem(id: Int){
        defaultBasketRepository.minusOneBasket(id)
        _baskets.value = defaultBasketRepository.getBaskets()
    }

    @AssistedFactory
    interface Factory{
        fun create(navigator: Navigator, screen: BaseScreen): BasketViewModel
    }

    companion object{
        fun provideBasketViewModelFactory(factory: Factory,navigator: Navigator, screen: BaseScreen) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(navigator, screen) as T
                }
            }
        }
    }

}