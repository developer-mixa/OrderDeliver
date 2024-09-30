package com.example.orderdeliver.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.orderdeliver.presentation.plugins.core.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentMainBinding
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding<FragmentMainBinding>()

    class Screen: BaseScreen

    private val viewModel: MainViewModel by viewModels()

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