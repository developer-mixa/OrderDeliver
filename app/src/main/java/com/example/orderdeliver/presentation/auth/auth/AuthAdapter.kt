package com.example.orderdeliver.presentation.auth.auth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AuthAdapter(
    fragment: Fragment,
    private val authFragments: List<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = authFragments.size

    override fun createFragment(position: Int): Fragment {
        return authFragments[position]
    }
}