package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.databinding.FragmentAddToBasketBinding
import com.example.orderdeliver.presentation.navigation.getBaseScreen
import com.example.orderdeliver.presentation.navigation.getMainNavigator
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddToBasketFragment : Fragment(R.layout.fragment_add_to_basket) {

    class Screen(val foodDataModel: FoodDataModel): BaseScreen

    @Inject
    lateinit var factory: AddToBasketViewModel.Factory

    private val viewModel: AddToBasketViewModel by viewModels {
        AddToBasketViewModel.provideBasketViewModelFactory(factory, screen = getBaseScreen() as Screen, navigator = getMainNavigator())
    }

    private val binding by viewBinding<FragmentAddToBasketBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFoodModel()
        onClickListeners()
    }

    private fun onClickListeners() = with(binding){
        buttonBack.setOnClickListener { viewModel.back() }
        buttonAddToBasket.setOnClickListener { viewModel.addBasket() }
    }

    private fun observeFoodModel(){
        viewModel.initialFoodModelEvent.observe(viewLifecycleOwner){
            binding.apply {
                val foodDataModel = it.getValue()
                if (foodDataModel != null){
                    foodPhoto.setImageResource(foodDataModel.imageResource)
                    nameFood.text = foodDataModel.name
                    descFood.text = foodDataModel.description
                    buttonAddToBasket.text = "В корзину за ${foodDataModel.price} $"
                }
            }
        }
    }

}