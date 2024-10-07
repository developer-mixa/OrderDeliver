package com.example.orderdeliver.presentation.auth.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentAuthBinding
import com.example.orderdeliver.presentation.auth.signin.SignInFragment
import com.example.orderdeliver.presentation.auth.signup.SignUpFragment
import com.example.orderdeliver.presentation.plugins.core.BaseScreen
import com.example.orderdeliver.presentation.views.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding: FragmentAuthBinding by viewBinding()
    private var pagerAdapter: AuthAdapter? = null

    private val authFragments get() = listOf(
        getString(R.string.sign_in) to SignInFragment.instance(),
        getString(R.string.sign_up) to SignUpFragment.instance()
    )

    class Screen: BaseScreen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupTabs()
    }

    private fun setupAdapter(){
        pagerAdapter = AuthAdapter(this, authFragments.map { it.second })
    }

    private fun setupTabs() = with(binding){
        pager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = authFragments[position].first
        }.attach()
    }

}