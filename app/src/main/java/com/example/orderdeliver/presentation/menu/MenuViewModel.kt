package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.FoodSource
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MenuViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted screen: BaseScreen,
    private val basketRepository: DefaultBasketRepository
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

    fun launchToAddBasket(foodDataModel: FoodDataModel) {
        navigator.launch(screen = AddToBasketFragment.Screen(foodDataModel),addToBackStack = true, aboveAll = true)
    }

    fun addBasket(foodDataModel: FoodDataModel){
        basketRepository.addBasket(foodDataModel)
    }

    @AssistedFactory
    interface Factory{
        fun create(navigator: Navigator, screen: BaseScreen): MenuViewModel
    }

    companion object{
        fun provideMenuViewModelFactory(factory: Factory,navigator: Navigator, screen: BaseScreen) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(navigator, screen) as T
                }
            }
        }
    }

}