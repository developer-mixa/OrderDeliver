package com.example.orderdeliver.utils

import android.content.Context
import android.graphics.Color
import androidx.cardview.widget.CardView
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodOption
import com.example.orderdeliver.domain.exceptions.LimitOptionsException

/**
 * Helps to make the functionality of choosing items in custom lists.
 *
 * @param context -> context to get color.
 * @param firstSelectView -> first view which must be selected.
 */
class CardViewSelector(private val context: Context, firstSelectView: CardView) {

    private var _selectView = firstSelectView

    init {
        _selectView.setCardBackgroundColor(Color.WHITE)
    }

    fun chooseView(view: CardView){
        _selectView.setCardBackgroundColor(context.getColor(R.color.option_background))
        view.setCardBackgroundColor(Color.WHITE)
        _selectView = view
    }

}