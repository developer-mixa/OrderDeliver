package com.example.orderdeliver.presentation.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigation.BaseScreen
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.PaymentModel
import com.example.orderdeliver.domain.repositories.BasketRepository
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.repositories.PaymasterRepository
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.repositories.FoodRepository
import com.example.orderdeliver.domain.takeSuccess
import com.example.orderdeliver.domain.usecases.GetAllPriceUseCase
import com.example.orderdeliver.domain.usecases.TapToMenuUseCase
import com.example.orderdeliver.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BasketViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted val screen: BaseScreen,
    private val basketRepository: BasketRepository,
    private val foodRepository: FoodRepository,
    private val paymasterRepository: PaymasterRepository,
    private val tapToMenuUseCase: TapToMenuUseCase,
    private val getAllPriceUseCase: GetAllPriceUseCase
): ViewModel() {

    private val _baskets: MutableLiveData<List<BasketModel>?> = MutableLiveData()
    val baskets = _baskets.share()

    private val _payment: MutableLiveData<Container<PaymentModel>> = MutableLiveData()
    val payment = _payment.share()

    init {
        viewModelScope.launch {
            basketRepository.listenBaskets().collect{
                _baskets.value = it
            }
        }
    }

    fun getPayment() = viewModelScope.launch{
        _payment.value = PendingContainer()
        val allBasketsCount = basketRepository.listenAllCount().first()
        _payment.value = paymasterRepository.getPayment(_baskets.value ?: emptyList(), allBasketsCount)
    }

    fun toMainMenu(){
        viewModelScope.launch {
            tapToMenuUseCase.toMain()
        }
    }

    fun getPriceForAll(): Int{
        if (_baskets.value == null) return 0

        return getAllPriceUseCase(_baskets.value!!)
    }

    fun addCountItem(foodDataModel: FoodDataModel) = viewModelScope.launch{
        try {
            basketRepository.addBasket(foodDataModel)
        }catch (_: ReachedLimitException){
            navigator.toast(R.string.reached_limit_text)
        }
    }

    fun removeCountItem(id: String)= viewModelScope.launch{
        basketRepository.minusOneBasket(id)
    }

    fun pay() = viewModelScope.launch{
        val payment = _payment.value.takeSuccess() ?: return@launch
        val result = paymasterRepository.pay(payment)
        if (result is SuccessContainer){
            _baskets.value?.forEach {basket ->
                foodRepository.reduceFood(basket.foodDataModel.id, basket.count)
            }
            basketRepository.clear()
        }
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