package com.example.orderdeliver.presentation.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.di.Singletons
import com.example.orderdeliver.domain.usecases.TapToMenuUseCase
import com.example.orderdeliver.presentation.basket.BasketFragment
import com.example.orderdeliver.presentation.menu.MenuFragment
import com.example.orderdeliver.presentation.profile.ProfileFragment
import com.example.orderdeliver.share
import com.example.orderdeliver.showLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel (
    private val navigator: Navigator,
    screen: MainFragment.Screen,
): BaseViewModel() {

    private val _isTapToMain: MutableLiveData<Boolean> = MutableLiveData()
    val isTapToMain = _isTapToMain.share()

    init {
        viewModelScope.launch {
            Singletons.tapToMenuUseCase.listen().collect {
                _isTapToMain.value = it.getValue()
            }
        }
    }

    fun openMenu()= navigator.launch(MenuFragment.Screen())

    fun openBasket() = navigator.launch(BasketFragment.Screen())

    fun openProfile() = navigator.launch(ProfileFragment.Screen())

}