package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.View
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.databinding.FragmentAddToBasketBinding
import com.example.orderdeliver.presentation.navigation.screenViewModel
import com.example.orderdeliver.presentation.views.viewBinding

class AddToBasketFragment : BaseFragment(R.layout.fragment_add_to_basket) {

    class Screen(val foodDataModel: FoodDataModel): BaseScreen

    override val viewModel: AddToBasketViewModel by screenViewModel()
    private val binding by viewBinding<FragmentAddToBasketBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFoodModel()
        onClickListeners()
    }

    private fun onClickListeners(){
        binding.buttonBack.setOnClickListener { viewModel.back() }
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