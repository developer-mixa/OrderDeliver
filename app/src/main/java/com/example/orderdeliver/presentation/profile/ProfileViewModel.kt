package com.example.orderdeliver.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderdeliver.data.sources.DefaultOrderStorySource
import com.example.orderdeliver.domain.sources.OrderStorySource
import com.example.orderdeliver.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val orderStory: OrderStorySource
) : ViewModel() {

    private val _orderStoryCount: MutableLiveData<Int> = MutableLiveData(0)
    val orderStoryCount = _orderStoryCount.share()

    init {
        _orderStoryCount.value = orderStory.getOrders().size
    }

}
