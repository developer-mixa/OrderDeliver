package com.example.orderdeliver.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentMainBinding
import com.example.orderdeliver.presentation.basket.BasketViewModel
import com.example.orderdeliver.presentation.navigation.getBaseScreen
import com.example.orderdeliver.presentation.navigation.getMainNavigator
import com.example.orderdeliver.presentation.navigation.screenViewModel
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding<FragmentMainBinding>()

    class Screen: BaseScreen

    @Inject lateinit var factory: MainViewModel.Factory

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideBasketViewModelFactory(factory, screen = getBaseScreen(), navigator = getMainNavigator())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlFragmentsByBottomView()
        observe()
    }

    private fun observe(){
        viewModel.isTapToMain.observe(viewLifecycleOwner){toMain->
            if (toMain)binding.bottomNavigationView.selectedItemId = R.id.main_menu
        }

        viewModel.isCountInBasket.observe(viewLifecycleOwner){basketCount->
            binding.textCounterBasket.isVisible = basketCount != 0
            binding.textCounterBasket.text = basketCount.toString()
        }

    }

    private fun controlFragmentsByBottomView(){
        viewModel.openMenu()

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.main_menu-> viewModel.openMenu()
                R.id.profile -> viewModel.openProfile()
                R.id.basket -> viewModel.openBasket()
            }
            true
        }
    }

}