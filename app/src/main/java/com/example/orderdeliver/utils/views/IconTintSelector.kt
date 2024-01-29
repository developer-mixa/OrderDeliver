package com.example.orderdeliver.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView

class IconTintSelector(
    override val context: Context,
    override val chooseColor: Int,
    override val turnedOffColor: Int,
    firstSelectedImageView: ImageView
) : ViewSelector{
    private var _selectView = firstSelectedImageView

    private val _chooseColor = context.getColor(chooseColor)

    private val _turnedOffColor = context.getColor(turnedOffColor)
    init {
        firstSelectedImageView.setColorFilter(_chooseColor)
    }

    override fun <T : View> chooseView(view: T) {

        view as ImageView

        _selectView.setColorFilter(_turnedOffColor)
        view.setColorFilter(_chooseColor)
        _selectView = view
    }
}