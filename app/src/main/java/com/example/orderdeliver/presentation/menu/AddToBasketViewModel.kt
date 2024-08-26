package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.usecases.AddToBasketUseCase
import com.example.orderdeliver.presentation.mappers.FoodToViewItemMapper
import com.example.orderdeliver.presentation.models.FoodItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class AddToBasketViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted private val screen: AddToBasketFragment.Screen,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val foodToViewItemMapper: FoodToViewItemMapper
) : ViewModel() {

    private val _currentFood = MutableLiveData<FoodDataModel>()

    val foodPrice: LiveData<FoodItem> = _currentFood.map {
        foodToViewItemMapper.map(it)
    }

    init {
        _currentFood.value = screen.foodDataModel
    }

    fun back() = navigator.goBack()
    fun addBasket() = viewModelScope.launch {

        try {
            addToBasketUseCase.invoke(_currentFood.value!!)
        }catch (_: ReachedLimitException) {
            navigator.toast(R.string.reached_limit_text)
        }catch (_ : NullPointerException){
            navigator.toast("The good wasn't added to basket.")
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