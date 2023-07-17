package com.example.orderdeliver.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentMenuBinding
import com.example.orderdeliver.presentation.views.viewBinding


class MenuFragment : Fragment(R.layout.fragment_menu) {

    class Screen: BaseScreen

    private val binding by viewBinding<FragmentMenuBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}