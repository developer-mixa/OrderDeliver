package com.example.orderdeliver.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.di.Singletons
import kotlinx.coroutines.launch

class BasketViewModel: ViewModel() {

    private val defaultBasketRepository = DefaultBasketRepository()

    fun toMainMenu(){
        viewModelScope.launch {
            Singletons.tapToMenuUseCase.toMain()
        }
    }

}