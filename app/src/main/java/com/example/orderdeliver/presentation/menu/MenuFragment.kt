package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.databinding.FragmentMenuBinding
import com.example.orderdeliver.presentation.menu.adapter.MenuAdapter
import com.example.orderdeliver.presentation.menu.adapter.TypeFoodAdapter
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.presentation.navigation.screenViewModel
import com.example.orderdeliver.presentation.views.viewBinding


class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    class Screen : BaseScreen

    private val binding by viewBinding<FragmentMenuBinding>()

    private val menuAdapter = MenuAdapter()
    private val typeFoodAdapter = TypeFoodAdapter()

    override val viewModel by screenViewModel<MenuViewModel>()

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
            menuAdapter.foods = foods as MutableList<FoodDataModel>
            menuAdapter.notifyDataSetChanged()
        }

        viewModel.typeFoods.observe(viewLifecycleOwner) { types ->
            typeFoodAdapter.types = types as MutableList<TypeFoodModel>
            typeFoodAdapter.notifyDataSetChanged()
        }

        binding.foodsRcView.layoutManager = verticalLayoutManager
        binding.foodsRcView.adapter = menuAdapter

        binding.typesRcView.layoutManager = horizontalLayoutManager
        binding.typesRcView.adapter = typeFoodAdapter

    }

}