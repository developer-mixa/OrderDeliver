package com.example.orderdeliver.presentation.delivery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.orderdeliver.presentation.plugins.core.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentPlaceDeliveryBinding
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDeliveryFragment : Fragment(R.layout.fragment_place_delivery) {

    class Screen: BaseScreen

    private val binding: FragmentPlaceDeliveryBinding by viewBinding()

    private val viewModel: PlaceDeliveryViewModel by viewModels()

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