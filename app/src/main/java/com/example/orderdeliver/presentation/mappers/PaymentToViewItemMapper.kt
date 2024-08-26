package com.example.orderdeliver.presentation.mappers

import android.annotation.SuppressLint
import android.content.Context
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.PaymentModel
import com.example.orderdeliver.presentation.mappers.base.ToViewItemMapper
import com.example.orderdeliver.presentation.models.PaymentItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentToViewItemMapper @Inject constructor(
    @ApplicationContext private val context: Context
) : ToViewItemMapper<PaymentModel, PaymentItem> {
    
    @SuppressLint("StringFormatMatches")
    override fun map(item: PaymentModel): PaymentItem {
        return PaymentItem(
            item.address,
            context.getString(R.string.products_count, item.allCountSubjects),
            "${item.priceWithoutDiscount} $",
            "-${item.discountSum} $",
            "${item.donePrice} $"
        )
    }
    
}