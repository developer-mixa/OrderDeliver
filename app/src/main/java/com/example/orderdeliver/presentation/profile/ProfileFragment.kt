package com.example.orderdeliver.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.orderdeliver.presentation.plugins.core.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentProfileBinding
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.utils.CardViewSelector
import com.example.orderdeliver.utils.IconTintSelector
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    class Screen : BaseScreen

    private val binding by viewBinding<FragmentProfileBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderSelectors()
    }


    private fun renderSelectors() = with(binding){
        val cardViewSelector = CardViewSelector(
            requireContext(), R.color.orange, R.color.white, cardOrderStory
        )

        val iconTintSelector = IconTintSelector(
            requireContext(), R.color.white, R.color.blue, imageOrderStory
        )


        fun chooseView(cardView: View, imageView: ImageView){
            cardViewSelector.chooseView(cardView as CardView)
            iconTintSelector.chooseView(imageView)
        }

        cardAddress.setOnClickListener {
            chooseView(it, imageAddress)
        }

        cardFav.setOnClickListener {
            chooseView(it, imageFav)
        }

        cardOrderStory.setOnClickListener {
            chooseView(it, imageOrderStory)
        }
    }

}