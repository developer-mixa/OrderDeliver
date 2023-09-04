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
import com.example.orderdeliver.data.models.PaymentModel
import com.example.orderdeliver.domain.BasketRepository
import com.example.orderdeliver.domain.usecases.TapToMenuUseCase
import com.example.orderdeliver.utils.share
import com.example.orderdeliver.utils.showLog
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BasketViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted val screen: BaseScreen,
    private val defaultBasketRepository: BasketRepository,
    private val tapToMenuUseCase: TapToMenuUseCase,
    private val def: BasketRepository
): ViewModel() {

    private val _baskets: MutableLiveData<List<BasketModel>?> = MutableLiveData<List<BasketModel>?>()
    val baskets = _baskets.share()

    private val _payment: MutableLiveData<PaymentModel> = MutableLiveData()
    val payment = _payment.share()

    private val _wayPayment: MutableLiveData<String> = MutableLiveData()
    val wayPayment = _wayPayment.share()

    init {
        viewModelScope.launch {
            defaultBasketRepository.listenBaskets().collect{
                _baskets.value = it
            }
        }
    }

    fun getPayment() = viewModelScope.launch{
        _payment.value = defaultBasketRepository.getPayment()
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

    fun addCountItem(foodDataModel: FoodDataModel) = viewModelScope.launch{
        defaultBasketRepository.addBasket(foodDataModel)

    }

    fun removeCountItem(id: Int)= viewModelScope.launch{
        defaultBasketRepository.minusOneBasket(id)
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