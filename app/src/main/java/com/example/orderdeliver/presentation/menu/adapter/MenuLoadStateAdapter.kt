package com.example.orderdeliver.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.orderdeliver.databinding.ErrorContainerBinding

typealias TryAgainAction = () -> Unit

class MenuLoadStateAdapter(
    private val tryAgainAction: TryAgainAction
) : LoadStateAdapter<MenuLoadStateAdapter.Holder>() {

    class Holder(
        private val binding: ErrorContainerBinding,
        private val tryAgainAction: TryAgainAction
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tryAgainButton.setOnClickListener { tryAgainAction() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            messageTextView.isVisible = loadState is LoadState.Error
            tryAgainButton.isVisible = loadState is LoadState.Error
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ErrorContainerBinding.inflate(inflater, parent, false)
        return Holder(binding, tryAgainAction)
    }

}