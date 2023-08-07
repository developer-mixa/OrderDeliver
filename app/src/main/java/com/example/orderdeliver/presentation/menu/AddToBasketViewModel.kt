package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.navigation.Event
import com.example.navigation.Navigator
import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.domain.usecases.AddToBasketUseCase
import com.example.orderdeliver.share
import com.example.orderdeliver.showLog
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AddToBasketViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted private val screen: AddToBasketFragment.Screen,
    private val addToBasketUseCase: AddToBasketUseCase,
    ): ViewModel() {

    private val _initialFoodModelEvent = MutableLiveData<Event<FoodDataModel>>()
    val initialFoodModelEvent = _initialFoodModelEvent.share()

    init {
        showLog(screen.foodDataModel.name)
        _initialFoodModelEvent.value = Event(screen.foodDataModel)
    }

    fun back() = navigator.goBack()
    fun addBasket(){
        addToBasketUseCase.invoke(screen.foodDataModel)
        navigator.goBack()
    }
    @AssistedFactory
    interface Factory{
        fun create(navigator: Navigator, screen: AddToBasketFragment.Screen): AddToBasketViewModel
    }

    companion object{
        fun provideBasketViewModelFactory(factory: Factory, navigator: Navigator, screen: AddToBasketFragment.Screen) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(navigator, screen) as T
                }
            }
        }
    }

}