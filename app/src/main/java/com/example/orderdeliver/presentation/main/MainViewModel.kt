package com.example.orderdeliver.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigation.BaseScreen
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.domain.BasketRepository
import com.example.orderdeliver.domain.usecases.TapToMenuUseCase
import com.example.orderdeliver.presentation.basket.BasketFragment
import com.example.orderdeliver.presentation.menu.MenuFragment
import com.example.orderdeliver.presentation.profile.ProfileFragment
import com.example.orderdeliver.utils.share
import com.example.orderdeliver.utils.showLog
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted screen: BaseScreen,
    private val tapToMenuUseCase: TapToMenuUseCase,
    private val defaultBasketRepository: BasketRepository,
) : ViewModel() {

    private val _isTapToMain: MutableLiveData<Boolean> = MutableLiveData()
    val isTapToMain = _isTapToMain.share()

    private val _isCountInBasket: MutableLiveData<Int> = MutableLiveData()
    val isCountInBasket = _isCountInBasket.share()

    init {
        viewModelScope.launch {
            showLog("init")
            tapToMenuUseCase.listen().collect {
                _isTapToMain.value = it.getValue()
            }
        }
        viewModelScope.launch {
            defaultBasketRepository.listenAllCount().collect {
                _isCountInBasket.value = it
            }
        }
    }

    fun openMenu() = navigator.launch(MenuFragment.Screen())

    fun openBasket() = navigator.launch(BasketFragment.Screen())

    fun openProfile() = navigator.launch(ProfileFragment.Screen())


    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator, screen: BaseScreen): MainViewModel


    }

    companion object {
        fun provideBasketViewModelFactory(
            factory: Factory,
            navigator: Navigator,
            screen: BaseScreen,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(navigator, screen) as T
                }
            }
        }
    }
}



