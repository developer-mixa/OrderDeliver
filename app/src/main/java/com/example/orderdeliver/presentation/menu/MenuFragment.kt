package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.databinding.FragmentMenuBinding
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.utils.getHorizontalLayoutManager
import com.example.orderdeliver.utils.getVerticalLayoutManager
import com.example.orderdeliver.presentation.menu.adapter.FoodActionState
import com.example.orderdeliver.presentation.menu.adapter.MenuAdapter
import com.example.orderdeliver.presentation.menu.adapter.MenuLoadStateAdapter
import com.example.orderdeliver.presentation.menu.adapter.TypeFoodAdapter
import com.example.orderdeliver.presentation.menu.adapter.TypeFoodState
import com.example.orderdeliver.presentation.navigation.getBaseScreen
import com.example.orderdeliver.presentation.navigation.getMainNavigator
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.utils.collectFlow
import com.example.orderdeliver.utils.showLog
import com.example.orderdeliver.utils.simpleScan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    class Screen : BaseScreen

    private val binding by viewBinding<FragmentMenuBinding>()

    @Inject
    lateinit var factory: MenuViewModel.Factory

    private val viewModel: MenuViewModel by viewModels {
        MenuViewModel.provideMenuViewModelFactory(
            factory,
            getMainNavigator(),
            getBaseScreen()
        )
    }


    private val typeFoodState = object : TypeFoodState {
        override fun tap(id: String, foodTypeId: String) {
            viewModel.setStateTypesById(id)
            viewModel.filterFoods(foodTypeId)
        }
    }

    private val foodActionState = object : FoodActionState {
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
        observer()
        setupAdapters()
        handleScrollingToTopWhenFiltering(menuAdapter)
        onClickListeners()
    }

    private fun onClickListeners() = with(binding){
        selectCityCard.setOnClickListener {
            viewModel.launchToPlaceDelivery()
        }

        buttonTryLoadFoodTypes.setOnClickListener {
            viewModel.getTypeFoods()
        }

        showLog("asdasdasdasd")
        loadStateContainer.tryAgainAction {
            showLog("asdasdasdasd")
            viewModel.filterFoods()
        }
    }

    private fun observer() = with(binding) {

        collectFlow {
            viewModel.foods.collect {
                menuAdapter.submitData(it)
            }
        }

        viewModel.typeFoods.observe(viewLifecycleOwner) { typesContainer ->

            progressBarTypeFoods.isVisible = typesContainer is PendingContainer
            buttonTryLoadFoodTypes.isVisible = typesContainer is ErrorContainer
            typesRcView.isVisible = typesContainer is SuccessContainer
            if (typesContainer is SuccessContainer){
                typeFoodAdapter.updateList(typesContainer.data)
            }
        }

        viewModel.currentCity.observe(viewLifecycleOwner) { currentCity ->
            cityText.text = currentCity
        }

    }


    private fun setupAdapters() = with(binding) {
        val tryAgainAction = { menuAdapter.retry() }

        val footerAdapter = MenuLoadStateAdapter(tryAgainAction)

        val adapterWithLoadState = menuAdapter.withLoadStateFooter(footerAdapter)

        foodsRcView.layoutManager = getVerticalLayoutManager()
        foodsRcView.adapter = adapterWithLoadState

        typesRcView.layoutManager = getHorizontalLayoutManager()
        typesRcView.setHasFixedSize(true)
        typesRcView.adapter = typeFoodAdapter
    }

    private fun handleScrollingToTopWhenFiltering(adapter: MenuAdapter) = lifecycleScope.launch {
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if(currentState != null)
                    binding.loadStateContainer.defaultHandleLoadState(currentState)
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.foodsRcView.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadStateFlow(adapter: MenuAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }

}