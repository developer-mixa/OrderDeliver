package com.example.orderdeliver.presentation.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.orderdeliver.presentation.plugins.core.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentAddToBasketBinding
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddToBasketFragment : Fragment(R.layout.fragment_add_to_basket) {

    class Screen(val foodDataModel: FoodDataModel) : BaseScreen

    private val viewModel: AddToBasketViewModel by viewModels()

    private val binding by viewBinding<FragmentAddToBasketBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFoodModel()
        onClickListeners()
    }

    private fun onClickListeners() = with(binding) {
        buttonBack.setOnClickListener { viewModel.back() }
        buttonAddToBasket.setOnClickListener { viewModel.addBasket() }
    }

    @SuppressLint("StringFormatMatches")
    private fun observeFoodModel() {
        viewModel.foodPrice.observe(viewLifecycleOwner) { currentFood->
            binding.apply {
                currentFood.run {
                    foodPhoto.setImageResource(imageResource)
                    nameFood.text = name
                    descFood.text = description
                    buttonAddToBasket.text = getString(R.string.add_to_basket, finalPrice)
                }
            }
        }
    }


}
