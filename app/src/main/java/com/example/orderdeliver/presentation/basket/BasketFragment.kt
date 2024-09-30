package com.example.orderdeliver.presentation.basket

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.orderdeliver.presentation.plugins.core.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.databinding.BottomSheetBasketBinding
import com.example.orderdeliver.databinding.FragmentBasketBinding
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.presentation.models.PaymentItem
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.utils.getVerticalLayoutManager
import com.example.orderdeliver.utils.markButtonDisable
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val binding: FragmentBasketBinding by viewBinding()

    private lateinit var dialog: BottomSheetDialog

    private val viewModel: BasketViewModel by viewModels()

    private val basketCountState = object : BasketCountState {
        override fun plus(foodDataModel: FoodDataModel) {
            viewModel.addCountItem(foodDataModel)
        }

        override fun minus(id: String) {
            viewModel.removeCountItem(id)
        }

    }

    private val adapter = BasketAdapter(basketCountState)
    private var animationFadeIn: Animation? = null

    class Screen : BaseScreen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        onClickListeners()
        observe()
    }

    private fun init() = with(binding) {
        animationFadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

        rcView.layoutManager = getVerticalLayoutManager()
        (rcView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        (rcView.itemAnimator as SimpleItemAnimator).removeDuration = 1
        rcView.adapter = adapter
    }

    private fun onClickListeners() {
        binding.buttonToMenu.setOnClickListener { viewModel.toMainMenu() }
        binding.buttonBuy.setOnClickListener { viewModel.getPayment() }
    }

    private fun showPayment(payment: PaymentItem) {


        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_basket, null)
        val dialogBinding = BottomSheetBasketBinding.bind(dialogView)

        dialogBinding.paymentContainer.isVisible = true
        dialogBinding.wayPaymentContainer.isVisible = false
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)



        dialogBinding.apply {

            fun hide(){
                binding.root.layoutTransition =
                    LayoutTransition().apply { setAnimateParentHierarchy(false) }

                paymentContainer.isVisible = false
                wayPaymentContainer.isVisible = true
            }

            textAdress.text = payment.address
            textCountSubjects.text = payment.countOrdersText
            textPriceWithoutDiscount.text = payment.withoutDiscountPriceText
            textDiscount.text = payment.discountSumText
            textPriceOrder.text = payment.finalPriceText

            paymentLayout.setOnClickListener {
                hide()
            }

            buttonMakeOrder.setOnClickListener {
                viewModel.pay()
                dialog.cancel()
            }

            imageBack.setOnClickListener {
                hide()
            }

        }

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    private fun observe() {
        viewModel.baskets.observe(viewLifecycleOwner) { baskets ->

            setVisibilities(baskets?.size == 0)

            binding.buttonBuy.text = "Оформить за ${viewModel.getPriceForAll()} $"
            if (baskets != null) adapter.updateList(baskets)
        }

        viewModel.payment.observe(viewLifecycleOwner) { resultPayment ->

            when (resultPayment) {
                is SuccessContainer<PaymentItem> -> {
                    setLoadingStatePendingButton(true)
                    showPayment(resultPayment.data)
                }

                is PendingContainer -> {
                    setLoadingStatePendingButton(false)
                }

                is ErrorContainer -> {
                    // TODO (HANDLE THE ERROR)
                }
            }


        }

    }

    private fun setLoadingStatePendingButton(isLoaded: Boolean) = with(binding) {
        buttonBuy.markButtonDisable(isLoaded)
        progressBarPayment.isVisible = !isLoaded
    }

    private fun setVisibilities(sizeIsZero: Boolean) = with(binding) {

        if (emptyContainer.isVisible != sizeIsZero) {
            emptyContainer.isVisible = sizeIsZero

            if (animationFadeIn != null) emptyContainer.startAnimation(animationFadeIn)

        }

        if (mainContainer.isVisible != !sizeIsZero) mainContainer.isVisible = !sizeIsZero
        if (buttonBuy.isVisible != !sizeIsZero) buttonBuy.isVisible = !sizeIsZero
    }

}