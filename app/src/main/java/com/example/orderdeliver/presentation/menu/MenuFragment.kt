package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.databinding.FragmentMenuBinding
import com.example.orderdeliver.getHorizontalLayoutManager
import com.example.orderdeliver.getVerticalLayoutManager
import com.example.orderdeliver.presentation.menu.adapter.FoodActionState
import com.example.orderdeliver.presentation.menu.adapter.MenuAdapter
import com.example.orderdeliver.presentation.menu.adapter.TypeFoodAdapter
import com.example.orderdeliver.presentation.menu.adapter.TypeFoodState
import com.example.orderdeliver.presentation.navigation.getBaseScreen
import com.example.orderdeliver.presentation.navigation.getMainNavigator
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    class Screen : BaseScreen

    private val binding by viewBinding<FragmentMenuBinding>()

    @Inject lateinit var factory: MenuViewModel.Factory

    private val viewModel: MenuViewModel by viewModels{ MenuViewModel.provideMenuViewModelFactory(factory,getMainNavigator(), getBaseScreen()) }


    private val typeFoodState = object : TypeFoodState{
        override fun tap(id: Int,foodType: FoodType) {
            viewModel.setStateTypesById(id)
            viewModel.filterFoods(foodType)
        }
    }

    private val foodActionState = object : FoodActionState{
        override fun select(foodDataModel: FoodDataModel) {
            viewModel.launchToAddBasket(foodDataModel)
        }

        override fun addBasket(foodDataModel: FoodDataModel) {
            viewModel.addBasket(foodDataModel)
        }

    }

    private val typeFoodAdapter = TypeFoodAdapter(typeFoodState)
    private val menuAdapter = MenuAdapter(foodActionState)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {

        viewModel.foods.observe(viewLifecycleOwner) { foods ->
            menuAdapter.submitList(foods)
        }

        viewModel.typeFoods.observe(viewLifecycleOwner) { types ->
            typeFoodAdapter.updateList(types)
        }

        binding.foodsRcView.layoutManager = getVerticalLayoutManager()
        binding.foodsRcView.adapter = menuAdapter

        binding.typesRcView.layoutManager = getHorizontalLayoutManager()
        binding.typesRcView.setHasFixedSize(true)
        binding.typesRcView.adapter = typeFoodAdapter

    }

}