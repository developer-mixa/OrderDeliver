package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.data.FoodSource
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.share

class MenuViewModel(
    private val navigator: Navigator,
    screen: MenuFragment.Screen,
) : BaseViewModel() {

    private val foodSource = FoodSource()

    private val _foods: MutableLiveData<List<FoodDataModel>> by lazy { MutableLiveData() }
    val foods = _foods.share()

    private val _typeFoods: MutableLiveData<List<TypeFoodModel>> by lazy { MutableLiveData() }
    val typeFoods = _typeFoods.share()

    init {
        _foods.value = foodSource.getFoods(FoodType.ALL)
        _typeFoods.value = foodSource.getTypes()
    }

    fun setStateTypesById(id: Int){
        val newTypeFoods = foodSource.setActivatedTypeFoodById(id) ?: return
        _typeFoods.value = newTypeFoods
    }

    fun filterFoods(foodType: FoodType){
        _foods.value = foodSource.getFoods(foodType)
    }

    fun onAddToBasket(foodDataModel: FoodDataModel) {

        navigator.launch(screen = AddToBasketFragment.Screen(foodDataModel),addToBackStack = true, aboveAll = true)
    }

}