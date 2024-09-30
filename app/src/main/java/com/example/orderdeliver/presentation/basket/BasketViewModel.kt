package com.example.orderdeliver.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
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
import com.example.orderdeliver.domain.mapView
import com.example.orderdeliver.domain.takeSuccess
import com.example.orderdeliver.domain.usecases.GetAllPriceUseCase
import com.example.orderdeliver.domain.usecases.TapToMenuUseCase
import com.example.orderdeliver.presentation.mappers.BasketToListItemMapper
import com.example.orderdeliver.presentation.mappers.PaymentToViewItemMapper
import com.example.orderdeliver.presentation.models.BasketListItem
import com.example.orderdeliver.presentation.models.PaymentItem
import com.example.orderdeliver.presentation.plugins.plugins.ToastPlugin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val toasts: ToastPlugin,
    private val basketRepository: BasketRepository,
    private val paymasterRepository: PaymasterRepository,
    private val tapToMenuUseCase: TapToMenuUseCase,
    private val getAllPriceUseCase: GetAllPriceUseCase,
    private val basketToListItemMapper: BasketToListItemMapper,
    private val paymentToViewItemMapper: PaymentToViewItemMapper
): ViewModel() {

    private val _baskets: MutableLiveData<List<BasketModel>?> = MutableLiveData()
    val baskets: LiveData<List<BasketListItem>?> = _baskets.map {
        it?.map {basket ->
            basketToListItemMapper.map(basket)
        }
    }

    private val _payment: MutableLiveData<Container<PaymentModel>> = MutableLiveData()
    val payment: LiveData<Container<PaymentItem>> = _payment.map {
        it.mapView(paymentToViewItemMapper)
    }

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

    fun toMainMenu() = viewModelScope.launch{
        tapToMenuUseCase.toMain()
    }

    fun getPriceForAll(): Float{
        if (_baskets.value == null) return 0f

        return getAllPriceUseCase(_baskets.value!!)
    }

    fun addCountItem(foodDataModel: FoodDataModel) = viewModelScope.launch{
        try {
            basketRepository.addBasket(foodDataModel)
        }catch (_: ReachedLimitException){
            toasts.toast(R.string.reached_limit_text)
        }
    }

    fun removeCountItem(id: String)= viewModelScope.launch{
        basketRepository.minusOneBasket(id)
    }

    fun pay() = viewModelScope.launch{
        // TODO (HANDLE ERRORS)
        val payment = _payment.value.takeSuccess() ?: return@launch
        val result = paymasterRepository.pay(payment.baskets)
        if (result is SuccessContainer){
            basketRepository.clear()
        }
    }

}