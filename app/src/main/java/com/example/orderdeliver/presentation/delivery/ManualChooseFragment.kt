package com.example.orderdeliver.presentation.delivery

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentManualChooseBinding
import com.example.orderdeliver.domain.ErrorContainer
import com.example.orderdeliver.domain.PendingContainer
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.takeSuccess
import com.example.orderdeliver.presentation.navigation.screenViewModel
import com.example.orderdeliver.presentation.views.viewBinding
import com.example.orderdeliver.utils.getVerticalLayoutManager
import com.example.orderdeliver.utils.showLog

class ManualChooseFragment : BaseFragment(R.layout.fragment_manual_choose) {

    class Screen : BaseScreen

    private val binding by viewBinding<FragmentManualChooseBinding>()

    override val viewModel by screenViewModel<ManualChooseViewModel>()

    private val cityAdapter: CityAdapter = CityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        onClickListeners()
        observe()

    }

    private fun initRecyclerView() = with(binding) {
        rcView.layoutManager = getVerticalLayoutManager()
        rcView.adapter = cityAdapter
    }

    private fun onClickListeners() {
        binding.edAddress.addTextChangedListener {
            viewModel.submitSearchCity(it.toString())
        }

        binding.imageBack.setOnClickListener {
            viewModel.goBack()
        }

    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.suggests.collect { result ->


                when (result) {
                    is SuccessContainer -> {
                        val cities = result.data
                        val isEmptyCities = cities.isEmpty()

                        binding.rcView.isVisible = !isEmptyCities
                        binding.textStatus.isVisible = isEmptyCities

                        if (isEmptyCities && binding.edAddress.text.toString().isNotBlank()){
                            binding.textStatus.text = "Начните вводить адрес"
                        }else{
                            binding.textStatus.text = "Не можем найти такой адрес :("
                        }

                        cityAdapter.updateList(result.data)
                    }

                    is ErrorContainer -> showLog("exception")
                    is PendingContainer -> showLog("pending")
                }
            }
        }
    }

}