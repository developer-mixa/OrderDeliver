package com.example.orderdeliver.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orderdeliver.domain.repositories.BasketRepository
import com.example.orderdeliver.domain.usecases.TapToMenuUseCase
import com.example.orderdeliver.presentation.auth.auth.AuthFragment
import com.example.orderdeliver.presentation.basket.BasketFragment
import com.example.orderdeliver.presentation.menu.MenuFragment
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import com.example.orderdeliver.presentation.profile.ProfileFragment
import com.example.orderdeliver.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: NavigatorPlugin,
    private val tapToMenuUseCase: TapToMenuUseCase,
    private val defaultBasketRepository: BasketRepository,
) : ViewModel() {

    private val _isTapToMain: MutableLiveData<Boolean> = MutableLiveData()
    val isTapToMain = _isTapToMain.share()

    private val _isCountInBasket: MutableLiveData<Int> = MutableLiveData()
    val isCountInBasket = _isCountInBasket.share()

    init {
        viewModelScope.launch {
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

}