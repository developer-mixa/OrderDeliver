package com.example.orderdeliver.presentation.delivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentPlaceDeliveryBinding
import com.example.orderdeliver.presentation.navigation.screenViewModel
import com.example.orderdeliver.presentation.views.viewBinding

class PlaceDeliveryFragment : BaseFragment(R.layout.fragment_place_delivery) {

    class Screen: BaseScreen

    private val binding: FragmentPlaceDeliveryBinding by viewBinding()

    override val viewModel: PlaceDeliveryViewModel by screenViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListeners()
    }

    private fun onClickListeners() = with(binding){

        containerDeliver.setOnClickListener { viewModel.chooseDeliver() }

        containerRestaurant.setOnClickListener { viewModel.chooseRestaurant() }

        imageExit.setOnClickListener { viewModel.exit() }

    }

}