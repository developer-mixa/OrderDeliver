package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.usecases.AddToBasketUseCase
import com.example.orderdeliver.presentation.mappers.FoodToViewItemMapper
import com.example.orderdeliver.presentation.models.FoodItem
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import com.example.orderdeliver.presentation.plugins.plugins.ToastPlugin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class AddToBasketViewModel @Inject constructor(
    private val navigator: NavigatorPlugin,
    private val toasts: ToastPlugin,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val foodToViewItemMapper: FoodToViewItemMapper
) : ViewModel() {

    private val _currentFood = MutableLiveData<FoodDataModel>()

    val foodPrice: LiveData<FoodItem> = _currentFood.map {
        foodToViewItemMapper.map(it)
    }

    fun back() = navigator.goBack()
    fun addBasket() = viewModelScope.launch {

        try {
            addToBasketUseCase.invoke(_currentFood.value!!)
        }catch (_: ReachedLimitException) {
            toasts.toast(R.string.reached_limit_text)
        }catch (_ : NullPointerException){
            toasts.toast("The good wasn't added to basket.")
        }

        navigator.goBack()
    }

}