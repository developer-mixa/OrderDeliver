package com.example.orderdeliver.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.LoadStateViewBinding

typealias TryAgainAction = () -> Unit

class LoadStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: LoadStateViewBinding
    private var tryAgainAction: TryAgainAction? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.load_state_view, this, true)
        binding = LoadStateViewBinding.bind(this)
        binding.tryAgainButton.setOnClickListener {
            tryAgainAction?.invoke()
        }
    }

    fun tryAgainAction(tryAgainAction: TryAgainAction){
        this.tryAgainAction = tryAgainAction
    }

    fun defaultHandleLoadState(state: LoadState) = with(binding){
        progressBar.isVisible = state is LoadState.Loading
        messageTextView.isVisible = state is LoadState.Error
        tryAgainButton.isVisible = state is LoadState.Error
    }

}