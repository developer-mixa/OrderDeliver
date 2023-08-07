package com.example.orderdeliver.presentation.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.databinding.FragmentBasketBinding
import com.example.orderdeliver.getVerticalLayoutManager
import com.example.orderdeliver.presentation.navigation.ARG_SCREEN
import com.example.orderdeliver.presentation.navigation.MainNavigator
import com.example.orderdeliver.presentation.navigation.getBaseScreen
import com.example.orderdeliver.presentation.navigation.getMainNavigator
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val binding: FragmentBasketBinding by viewBinding()

    @Inject lateinit var factory: BasketViewModel.Factory

    private val viewModel: BasketViewModel by viewModels {
        BasketViewModel.provideBasketViewModelFactory(factory, screen = getBaseScreen(), navigator = getMainNavigator())
    }

    private val basketCountState = object : BasketCountState{
        override fun plus(foodDataModel: FoodDataModel) {
            viewModel.addCountItem(foodDataModel)
        }

        override fun minus(id: Int) {
            viewModel.removeCountItem(id)
        }

    }

    private val adapter = BasketAdapter(basketCountState)

    class Screen : BaseScreen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        onClickListeners()
        observe()
    }
    private fun initRecyclerView(){
        binding.rcView.layoutManager = getVerticalLayoutManager()
        (binding.rcView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        (binding.rcView.itemAnimator as SimpleItemAnimator).removeDuration = 1
        binding.rcView.adapter = adapter
    }
    private fun onClickListeners(){
        binding.buttonToMenu.setOnClickListener { viewModel.toMainMenu() }
    }
    private fun observe(){
        viewModel.baskets.observe(viewLifecycleOwner){baskets->

            binding.emptyContainer.isVisible = baskets == null
            binding.mainContainer.isVisible = baskets != null
            binding.buttonBuy.isVisible = baskets != null

            binding.buttonBuy.text = "Оформить за ${viewModel.getPriceForAll()} $"
            if (baskets != null) adapter.updateList(baskets)
        }

    }

}