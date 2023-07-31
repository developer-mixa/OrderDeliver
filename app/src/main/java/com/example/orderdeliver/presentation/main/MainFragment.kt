package com.example.orderdeliver.presentation.main

import android.os.Bundle
import android.view.View
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentMainBinding
import com.example.orderdeliver.di.Singletons
import com.example.orderdeliver.presentation.navigation.ARG_SCREEN
import com.example.orderdeliver.presentation.navigation.screenViewModel
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.showLog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val binding by viewBinding<FragmentMainBinding>()

    class Screen: BaseScreen

    override val viewModel by screenViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlFragmentsByBottomView()
        observe()
    }

    private fun observe(){
        viewModel.isTapToMain.observe(viewLifecycleOwner){toMain->
            if (toMain)binding.bottomNavigationView.selectedItemId = R.id.main_menu
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