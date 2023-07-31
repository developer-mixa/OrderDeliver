package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.databinding.FragmentMenuBinding
import com.example.orderdeliver.presentation.menu.adapter.FoodActionState
import com.example.orderdeliver.presentation.menu.adapter.MenuAdapter
import com.example.orderdeliver.presentation.menu.adapter.TypeFoodAdapter
import com.example.orderdeliver.presentation.menu.adapter.TypeFoodState
import com.example.orderdeliver.presentation.navigation.screenViewModel
import com.example.orderdeliver.presentation.views.viewBinding


class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    class Screen : BaseScreen

    private val binding by viewBinding<FragmentMenuBinding>()


    override val viewModel by screenViewModel<MenuViewModel>()


    private val typeFoodState = object : TypeFoodState{
        override fun tap(id: Int,foodType: FoodType) {
            viewModel.setStateTypesById(id)
            viewModel.filterFoods(foodType)
        }
    }

    private val foodActionState = object : FoodActionState{
        override fun select(foodDataModel: FoodDataModel) {
            viewModel.onAddToBasket(foodDataModel)
        }

    }

    private val typeFoodAdapter = TypeFoodAdapter(typeFoodState)
    private val menuAdapter = MenuAdapter(foodActionState)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val verticalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val horizontalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.foods.observe(viewLifecycleOwner) { foods ->
            menuAdapter.submitList(foods)
        }

        viewModel.typeFoods.observe(viewLifecycleOwner) { types ->
            typeFoodAdapter.updateList(types)
        }

        binding.foodsRcView.layoutManager = verticalLayoutManager
        binding.foodsRcView.adapter = menuAdapter

        binding.typesRcView.layoutManager = horizontalLayoutManager
        binding.typesRcView.setHasFixedSize(true)
        binding.typesRcView.adapter = typeFoodAdapter

    }

}