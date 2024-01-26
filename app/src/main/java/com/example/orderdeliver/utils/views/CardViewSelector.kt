package com.example.orderdeliver.utils

import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView

class CardViewSelector(
    override val context: Context,
    override val chooseColor: Int,
    override val turnedOffColor: Int,
    firstSelectView: CardView
) : ViewSelector {
    private var _selectView = firstSelectView

    private val _chooseColor = context.getColor(chooseColor)

    private val _turnedOffColor = context.getColor(turnedOffColor)
    init {
        _selectView.setCardBackgroundColor(_chooseColor)
    }

    override fun <T : View> chooseView(view: T) {

        view as CardView

        _selectView.setCardBackgroundColor(_turnedOffColor)
        view.setCardBackgroundColor(_chooseColor)
        _selectView = view
    }
}