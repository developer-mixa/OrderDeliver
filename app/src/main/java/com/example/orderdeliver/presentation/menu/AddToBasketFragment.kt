package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.FoodOption
import com.example.orderdeliver.domain.models.PizzaSize
import com.example.orderdeliver.domain.models.PizzaType
import com.example.orderdeliver.domain.models.SetPriceFood
import com.example.orderdeliver.databinding.AroundLinearLayoutBinding
import com.example.orderdeliver.databinding.FragmentAddToBasketBinding
import com.example.orderdeliver.databinding.TextOptionBinding
import com.example.orderdeliver.presentation.navigation.getBaseScreen
import com.example.orderdeliver.presentation.navigation.getMainNavigator
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.utils.CardViewSelector
import com.example.orderdeliver.utils.showLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddToBasketFragment : Fragment(R.layout.fragment_add_to_basket) {

    class Screen(val foodDataModel: FoodDataModel) : BaseScreen

    @Inject
    lateinit var factory: AddToBasketViewModel.Factory

    private val viewModel: AddToBasketViewModel by viewModels {
        AddToBasketViewModel.provideBasketViewModelFactory(
            factory,
            screen = getBaseScreen() as Screen,
            navigator = getMainNavigator()
        )
    }

    private val binding by viewBinding<FragmentAddToBasketBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFoodModel()
        observeFoodOptions()
        onClickListeners()
    }

    private fun onClickListeners() = with(binding) {
        buttonBack.setOnClickListener { viewModel.back() }
        buttonAddToBasket.setOnClickListener { viewModel.addBasket() }
    }

    private fun observeFoodModel() {
        viewModel.currentFood.observe(viewLifecycleOwner) {currentFood->
            showLog(currentFood.price.toString())
            binding.apply {
                currentFood.run {
                    foodPhoto.setImageResource(imageResource)
                    nameFood.text = name
                    descFood.text = description

                    if (priceWithDiscount == null)
                        buttonAddToBasket.text = "В корзину за $price $"
                    else buttonAddToBasket.text = "В корзину за $priceWithDiscount $"
                }
            }
        }
    }

    private fun observeFoodOptions() {
        viewModel.foodOptions.observe(viewLifecycleOwner) { foodOptions ->
            renderOptions(foodOptions)
        }
    }

    private fun renderOptions(foodOptions: Map<String, List<FoodOption>>) {
        /**First [forEach] for rows, included(second) [forEach] for columns*/

        /**the key of the column by which we will take the elements that are in it.*/
        foodOptions.keys.forEach {

            /**variable for defining the first selected view.*/
            var isFirst = true

            val aroundLinearBinding =
                AroundLinearLayoutBinding.inflate(LayoutInflater.from(requireContext()))

            /**class that provides it is possible to select elements.*/
            lateinit var cardViewSelector: CardViewSelector

            /**we get the options themselves in the column.*/
            foodOptions[it]?.forEach { foodOption ->
                val textOptionBinding =
                    TextOptionBinding.inflate(LayoutInflater.from(requireContext()))

                val text = when (foodOption) {
                    is PizzaSize -> foodOption.nameSize
                    is PizzaType -> foodOption.type
                }

                textOptionBinding.textNameOption.text = text

                if (foodOption is SetPriceFood && foodOption.changeDefault){
                    cardViewSelector.chooseView(textOptionBinding.root)
                }

                if (isFirst) cardViewSelector =
                    CardViewSelector(requireContext(), R.color.white, R.color.background_basket_color, textOptionBinding.root)
                isFirst = false

                textOptionBinding.root.setOnClickListener { cardView ->
                    cardViewSelector.chooseView(cardView as CardView)
                    viewModel.setPrice(foodOption)
                }

                /**we fill in our row from the view in the column.*/
                aroundLinearBinding.container.addView(textOptionBinding.root)

                /**in order for the views to occupy the same space, we configure params.*/
                val params = textOptionBinding.root.layoutParams as LinearLayout.LayoutParams
                params.weight = 1f / foodOptions[it]!!.size
                textOptionBinding.root.layoutParams = params
            }

            /**adding the filled column.*/
            binding.optionsContainer.addView(aroundLinearBinding.root)
        }
    }

}
