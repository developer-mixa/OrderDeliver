package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigation.Event
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodOption
import com.example.orderdeliver.domain.BasketRepository
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.helpers.FoodOptionsHelper
import com.example.orderdeliver.domain.usecases.AddToBasketUseCase
import com.example.orderdeliver.utils.share
import com.example.orderdeliver.utils.showLog
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddToBasketViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted private val screen: AddToBasketFragment.Screen,
    private val addToBasketUseCase: AddToBasketUseCase,
) : ViewModel() {

    private val _initialFoodModelEvent = MutableLiveData<Event<FoodDataModel>>()
    val initialFoodModelEvent = _initialFoodModelEvent.share()

    private val _foodOptions = MutableLiveData<Map<String,List<FoodOption>>>()
    val foodOptions = _foodOptions.share()

    init {
        _initialFoodModelEvent.value = Event(screen.foodDataModel)
        val options = screen.foodDataModel.options
        if (options != null) {
            val mapOptions = FoodOptionsHelper.toMap(options)
            _foodOptions.value = mapOptions
        }
    }

    fun back() = navigator.goBack()
    fun addBasket() = viewModelScope.launch {

        try {
            addToBasketUseCase.invoke(screen.foodDataModel)
        } catch (_: ReachedLimitException) {
            navigator.toast(R.string.reached_limit_text)
        }

        navigator.goBack()
    }

    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator, screen: AddToBasketFragment.Screen): AddToBasketViewModel
    }

    companion object {
        fun provideBasketViewModelFactory(
            factory: Factory,
            navigator: Navigator,
            screen: AddToBasketFragment.Screen
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(navigator, screen) as T
                }
            }
        }
    }

}