package com.example.orderdeliver.presentation.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentBasketBinding
import com.example.orderdeliver.presentation.views.viewBinding


class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val binding: FragmentBasketBinding by viewBinding()
    private val viewModel: BasketViewModel by viewModels()
    class Screen : BaseScreen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener { viewModel.toMainMenu() }
    }
}